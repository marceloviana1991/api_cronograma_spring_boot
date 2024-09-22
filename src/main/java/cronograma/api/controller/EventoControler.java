package cronograma.api.controller;

import cronograma.api.Repository.CronogramaRepository;
import cronograma.api.Repository.EventoRepository;
import cronograma.api.dto.EventoAtualizarDTO;
import cronograma.api.dto.EventoCadastrarDTO;
import cronograma.api.dto.EventoListarDTO;
import cronograma.api.model.Cronograma;
import cronograma.api.model.Evento;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/eventos")
public class EventoControler {

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private CronogramaRepository cronogramaRepository;

    @PostMapping
    @Transactional
    public void cadastrarEvento(@RequestBody @Valid EventoCadastrarDTO eventoCadastrarDTO) {
        Optional<Cronograma> cronogramaOptional = cronogramaRepository.findById(eventoCadastrarDTO.cronogramaId());
        if (cronogramaOptional.isPresent()) {
            Evento evento = new Evento(eventoCadastrarDTO);
            evento.setCronograma(cronogramaOptional.get());
            eventoRepository.save(evento);
        }
    }

    @GetMapping
    public List<EventoListarDTO> listarEventos(
            @PageableDefault(sort = {"diaDaSemana", "horario"}) Pageable pageable,
            @RequestParam(value = "cronogramaId", required = false) Long cronogramaId) {
        if (cronogramaId != null) {
            return eventoRepository.findBycronogramaId(cronogramaId, pageable).stream().map(EventoListarDTO::new).toList();
        }
        return eventoRepository.findAll(pageable).stream().map(EventoListarDTO::new).toList();
    }

    @PutMapping
    @Transactional
    public void atualizarEvento(@RequestBody @Valid EventoAtualizarDTO eventoAtualizarDTO) {
        Optional<Evento> eventoOptional = eventoRepository.findById(eventoAtualizarDTO.id());
        eventoOptional.ifPresent(evento -> evento.atualizar(eventoAtualizarDTO));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluirEvento(@PathVariable Long id) {
        eventoRepository.deleteById(id);
    }


}
