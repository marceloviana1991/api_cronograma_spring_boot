package cronograma.api.controller;

import cronograma.api.Repository.EventoRepository;
import cronograma.api.dto.EventoDTO;
import cronograma.api.model.Evento;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eventos")
public class EventoControler {

    @Autowired
    private EventoRepository eventoRepository;

    @PostMapping
    @Transactional
    public void cadastrarEvento(@RequestBody EventoDTO eventoDTO) {
        eventoRepository.save(new Evento(eventoDTO));
    }
}
