package cronograma.api.controller;

import cronograma.api.repository.CronogramaRepository;
import cronograma.api.service.TokenService;
import cronograma.api.dto.*;
import cronograma.api.infra.CronogramaTokenJWT;
import cronograma.api.model.Cronograma;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("usuarios")
public class ConogramaControler {

    @Autowired
    private CronogramaRepository cronogramaRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid CronogramaAutenticacaoDTO cronogramaAutenticacaoDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                cronogramaAutenticacaoDTO.login(), cronogramaAutenticacaoDTO.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Cronograma) authentication.getPrincipal());
        return ResponseEntity.ok(new CronogramaTokenJWT(tokenJWT));
    }

    @PostMapping("/cadastro")
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
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<List<CronogramaListarDTO>> listarCronogramas() {
        List<CronogramaListarDTO> cronogramaListarDTOList = cronogramaRepository.findAll().stream()
                .map(CronogramaListarDTO::new).toList();
        return ResponseEntity.ok(cronogramaListarDTOList);
    }

    @PutMapping("/bloquear/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<?> excluirCronograma(@PathVariable Long id) {
        Cronograma cronograma = cronogramaRepository.getReferenceById(id);
        cronograma.bloquar();
        return ResponseEntity.ok(new CronogramaDetalhamentoDTO(cronograma));
    }

}
