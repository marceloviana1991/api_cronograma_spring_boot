package cronograma.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cronogramas")
public class ConogramaControler {

    @GetMapping
    public String getCronogramas() {
        return "Hello World Spring!";
    }

}
