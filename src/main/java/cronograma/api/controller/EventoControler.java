package cronograma.api.controller;

import cronograma.api.Repository.CronogramaRepository;
import cronograma.api.Repository.EventoRepository;
import cronograma.api.Service.TokenService;
import cronograma.api.dto.EventoAtualizarDTO;
import cronograma.api.dto.EventoCadastrarDTO;
import cronograma.api.dto.EventoDetalhamentoDTO;
import cronograma.api.dto.EventoListarDTO;
import cronograma.api.model.Cronograma;
import cronograma.api.model.Evento;
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
public class EventoControler {

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private CronogramaRepository cronogramaRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity<EventoDetalhamentoDTO> cadastrarEvento(@RequestBody @Valid EventoCadastrarDTO
                                                                             eventoCadastrarDTO,
                                                                 UriComponentsBuilder uriComponentsBuilder,
                                                                 HttpServletRequest request) {
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var cronograma = (Cronograma) cronogramaRepository.findByLogin(subject);
        Evento evento = new Evento(eventoCadastrarDTO);
        evento.setCronograma(cronograma);
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
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var cronograma = (Cronograma) cronogramaRepository.findByLogin(subject);
        Evento evento = eventoRepository.getReferenceById(eventoAtualizarDTO.id());
        if (cronograma.equals(evento.getCronograma())) {
            evento.atualizar(eventoAtualizarDTO);
            return ResponseEntity.ok(new EventoDetalhamentoDTO((evento)));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluirEvento(@PathVariable Long id, HttpServletRequest request) {
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var cronograma = (Cronograma) cronogramaRepository.findByLogin(subject);
        Evento evento = eventoRepository.getReferenceById(id);
        if (cronograma.equals(evento.getCronograma())) {
            evento.excluir();
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
