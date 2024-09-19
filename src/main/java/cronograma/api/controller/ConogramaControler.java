package cronograma.api.controller;

import cronograma.api.Repository.CronogramaRepository;
import cronograma.api.dto.CronogramaDTO;
import cronograma.api.model.Cronograma;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cronogramas")
public class ConogramaControler {

    @Autowired
    private CronogramaRepository cronogramaRepository;

    @PostMapping
    @Transactional
    public void cadastrarCronograma(@RequestBody @Valid CronogramaDTO cronogramaDTO) {
        cronogramaRepository.save(new Cronograma(cronogramaDTO));
    }

}
