package cronograma.api.controller;

import cronograma.api.dto.EventoDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eventos")
public class EventoControler {

    @PostMapping
    public void cadastrarEvento(@RequestBody EventoDTO eventoDTO) {
        System.out.println(eventoDTO);
    }
}
