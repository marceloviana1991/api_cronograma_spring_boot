package cronograma.api.controller;

import cronograma.api.Repository.CronogramaRepository;
import cronograma.api.dto.CronogramaCadastrarDTO;
import cronograma.api.dto.CronogramaListarDTO;
import cronograma.api.model.Cronograma;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cronogramas")
public class ConogramaControler {

    @Autowired
    private CronogramaRepository cronogramaRepository;

    @PostMapping
    @Transactional
    public void cadastrarCronograma(@RequestBody @Valid CronogramaCadastrarDTO cronogramaCadastrarDTO) {
        cronogramaRepository.save(new Cronograma(cronogramaCadastrarDTO));
    }

    @GetMapping
    public List<CronogramaListarDTO> listarCronogramas() {
        return cronogramaRepository.findAll().stream().map(CronogramaListarDTO::new).toList();
    }

}
