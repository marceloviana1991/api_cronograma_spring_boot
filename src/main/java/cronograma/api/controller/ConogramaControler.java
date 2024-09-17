package cronograma.api.controller;

import cronograma.api.dto.CronogramaDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cronogramas")
public class ConogramaControler {

    @PostMapping
    public void cadastrarCronograma(@RequestBody CronogramaDTO cronogramaDTO) {
        System.out.println(cronogramaDTO);
    }

}
