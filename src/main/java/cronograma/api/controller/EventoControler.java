package cronograma.api.controller;

import cronograma.api.Repository.EventoRepository;
import cronograma.api.dto.EventoCadastrarDTO;
import cronograma.api.dto.EventoListarDTO;
import cronograma.api.model.Evento;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoControler {

    @Autowired
    private EventoRepository eventoRepository;

    @PostMapping
    @Transactional
    public void cadastrarEvento(@RequestBody @Valid EventoCadastrarDTO eventoCadastrarDTO) {
        eventoRepository.save(new Evento(eventoCadastrarDTO));
    }

    @GetMapping
    public List<EventoListarDTO> listarEventos() {
        return eventoRepository.findAll().stream().map(EventoListarDTO::new).toList();
    }
}
