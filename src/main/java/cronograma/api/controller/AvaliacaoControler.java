package cronograma.api.controller;

import cronograma.api.repository.AvaliacaoRepository;
import cronograma.api.repository.CronogramaRepository;
import cronograma.api.repository.EventoRepository;
import cronograma.api.service.AvaliacaoService;
import cronograma.api.service.TokenService;
import cronograma.api.dto.AvaliacaoAtualizarDTO;
import cronograma.api.dto.AvaliacaoCadastrarDTO;
import cronograma.api.dto.AvaliacaoDetalhamentoDTO;
import cronograma.api.dto.AvaliacaoListarDTO;
import cronograma.api.model.Avaliacao;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
@SecurityRequirement(name = "bearer-key")
public class AvaliacaoControler {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private CronogramaRepository cronogramaRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping
    @Transactional
    public ResponseEntity<AvaliacaoDetalhamentoDTO> cadastrarAvaliacao(
            @RequestBody @Valid AvaliacaoCadastrarDTO avaliacaoCadastrarDTO,
            UriComponentsBuilder uriComponentsBuilder,
            HttpServletRequest request) {
        Avaliacao avaliacao = avaliacaoService.instaciar(avaliacaoCadastrarDTO, request);
        avaliacaoRepository.save(avaliacao);
        var uri = uriComponentsBuilder.path("/avaliacoes/{id}").buildAndExpand(avaliacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new AvaliacaoDetalhamentoDTO(avaliacao));
    }

    @GetMapping
    public ResponseEntity<List<AvaliacaoListarDTO>> listarAvaliacoes(
            @RequestParam(value = "eventoId", required = false) Long eventoId,
            @PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (eventoId != null) {
            List<AvaliacaoListarDTO> avaliacaoListarDTOList = avaliacaoRepository.findByeventoId(eventoId, pageable)
                    .stream().map(AvaliacaoListarDTO::new).toList();
            return ResponseEntity.ok(avaliacaoListarDTOList);
        }
        List<AvaliacaoListarDTO> avaliacaoListarDTOList = avaliacaoRepository.findByEventoAtivoTrue(pageable).stream()
                .map(AvaliacaoListarDTO::new).toList();
        return ResponseEntity.ok(avaliacaoListarDTOList);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<AvaliacaoDetalhamentoDTO> atualizarAvaliacao(
            @RequestBody @Valid AvaliacaoAtualizarDTO avaliacaoAtualizarDTO,
            HttpServletRequest request) {
        Avaliacao avaliacao = avaliacaoService.atualizar(avaliacaoAtualizarDTO, request);
        if (avaliacao != null) {
            return ResponseEntity.ok(new AvaliacaoDetalhamentoDTO(avaliacao));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoDetalhamentoDTO> detalharAvaliacao(@PathVariable Long id) {
        Avaliacao avaliacao = avaliacaoRepository.getReferenceById(id);
        return ResponseEntity.ok(new AvaliacaoDetalhamentoDTO(avaliacao));
    }

}
