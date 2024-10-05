package cronograma.api.controller;

import cronograma.api.repository.EventoRepository;
import cronograma.api.service.EventoService;
import cronograma.api.dto.EventoAtualizarDTO;
import cronograma.api.dto.EventoCadastrarDTO;
import cronograma.api.dto.EventoDetalhamentoDTO;
import cronograma.api.dto.EventoListarDTO;
import cronograma.api.model.Evento;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/eventos")
@SecurityRequirement(name = "bearer-key")
public class EventoControler {

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private EventoService eventoService;

    @PostMapping
    @Transactional
    public ResponseEntity<EventoDetalhamentoDTO> cadastrarEvento(@RequestBody @Valid EventoCadastrarDTO
                                                                             eventoCadastrarDTO,
                                                                 UriComponentsBuilder uriComponentsBuilder,
                                                                 HttpServletRequest request) {
        Evento evento = eventoService.instanciar(eventoCadastrarDTO, request);
        eventoRepository.save(evento);
        var uri = uriComponentsBuilder.path("/eventos/{id}").buildAndExpand(evento.getId()).toUri();
        return ResponseEntity.created(uri).body(new EventoDetalhamentoDTO((evento)));
    }

    @GetMapping
    public ResponseEntity<List<EventoListarDTO>> listarEventos(
            @PageableDefault(sort = {"diaDaSemana", "horario"}) Pageable pageable,
            @RequestParam(value = "cronogramaId", required = false) Long cronogramaId) {
        if (cronogramaId != null) {
            List<EventoListarDTO> eventoListarDTOList = eventoRepository.findAllBycronogramaIdAndAtivoTrue(cronogramaId,
                            pageable).stream()
                    .map(EventoListarDTO::new).toList();
            return ResponseEntity.ok(eventoListarDTOList);
        }
        List<EventoListarDTO> eventoListarDTOList = eventoRepository.findAllByAtivoTrue(pageable).stream()
                .map(EventoListarDTO::new).toList();
        return ResponseEntity.ok(eventoListarDTOList);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<EventoDetalhamentoDTO> atualizarEvento(
            @RequestBody @Valid EventoAtualizarDTO eventoAtualizarDTO,
            HttpServletRequest request) {
        Evento evento = eventoService.atualizar(eventoAtualizarDTO, request);
        if (evento != null) {
            return ResponseEntity.ok(new EventoDetalhamentoDTO((evento)));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluirEvento(@PathVariable Long id, HttpServletRequest request) {
        Evento evento = eventoService.excluir(id, request);
        if (evento != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<EventoDetalhamentoDTO> detalharEvento(@PathVariable Long id) {
        Evento evento = eventoRepository.findByIdAndAtivoTrue(id);
        return ResponseEntity.ok(new EventoDetalhamentoDTO(evento));
    }

}
