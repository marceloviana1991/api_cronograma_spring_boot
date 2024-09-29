package cronograma.api.controller;

import cronograma.api.Repository.CronogramaRepository;
import cronograma.api.dto.*;
import cronograma.api.model.Cronograma;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/cronogramas")
public class ConogramaControler {

    @Autowired
    private CronogramaRepository cronogramaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<CronogramaDetalhamentoDTO> cadastrarCronograma(
            @RequestBody @Valid CronogramaCadastrarDTO cronogramaCadastrarDTO,
            UriComponentsBuilder uriComponentsBuilder) {
        Cronograma cronograma = new Cronograma(cronogramaCadastrarDTO);
        cronogramaRepository.save(cronograma);
        var uri = uriComponentsBuilder.path("/cronogramas/{id}").buildAndExpand(cronograma.getId()).toUri();
        return ResponseEntity.created(uri).body(new CronogramaDetalhamentoDTO(cronograma));
    }

    @GetMapping
    public ResponseEntity<List<CronogramaListarDTO>> listarCronogramas() {
        List<CronogramaListarDTO> cronogramaListarDTOList = cronogramaRepository.findAll().stream()
                .map(CronogramaListarDTO::new).toList();
        return ResponseEntity.ok(cronogramaListarDTOList);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<CronogramaDetalhamentoDTO> atualizarCronograma(
            @RequestBody @Valid CronogramaAtualizarDTO cronogramaAtualizarDTO) {
        Cronograma cronograma = cronogramaRepository.getReferenceById(cronogramaAtualizarDTO.id());
        cronograma.atualizar(cronogramaAtualizarDTO);
        return ResponseEntity.ok(new CronogramaDetalhamentoDTO(cronograma));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CronogramaDetalhamentoDTO> detalharCronograma(@PathVariable Long id) {
        Cronograma cronograma = cronogramaRepository.getReferenceById(id);
        return ResponseEntity.ok(new CronogramaDetalhamentoDTO(cronograma));
    }
}
