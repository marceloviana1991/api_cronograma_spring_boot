package cronograma.api.controller;

import cronograma.api.Repository.AvaliacaoRepository;
import cronograma.api.Repository.CronogramaRepository;
import cronograma.api.Repository.EventoRepository;
import cronograma.api.Service.TokenService;
import cronograma.api.dto.AvaliacaoAtualizarDTO;
import cronograma.api.dto.AvaliacaoCadastrarDTO;
import cronograma.api.dto.AvaliacaoDetalhamentoDTO;
import cronograma.api.dto.AvaliacaoListarDTO;
import cronograma.api.model.Avaliacao;
import cronograma.api.model.Cronograma;
import cronograma.api.model.Evento;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoControler {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private CronogramaRepository cronogramaRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Transactional
    public ResponseEntity<AvaliacaoDetalhamentoDTO> cadastrarAvaliacao(
            @RequestBody @Valid AvaliacaoCadastrarDTO avaliacaoCadastrarDTO,
            UriComponentsBuilder uriComponentsBuilder,
            HttpServletRequest request) {
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var cronograma = (Cronograma) cronogramaRepository.findByLogin(subject);
        Evento evento = eventoRepository.getReferenceById(avaliacaoCadastrarDTO.eventoId());
        Avaliacao avaliacao = new Avaliacao(avaliacaoCadastrarDTO);
        avaliacao.setCronograma(cronograma);
        avaliacao.setEvento(evento);
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
        List<AvaliacaoListarDTO> avaliacaoListarDTOList = avaliacaoRepository.findAll(pageable).stream()
                .map(AvaliacaoListarDTO::new).toList();
        return ResponseEntity.ok(avaliacaoListarDTOList);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<AvaliacaoDetalhamentoDTO> atualizarAvaliacao(
            @RequestBody @Valid AvaliacaoAtualizarDTO avaliacaoAtualizarDTO,
            HttpServletRequest request) {
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var cronograma = (Cronograma) cronogramaRepository.findByLogin(subject);
        Avaliacao avaliacao = avaliacaoRepository.getReferenceById(avaliacaoAtualizarDTO.id());
        if (cronograma.equals(avaliacao.getCronograma())) {
            avaliacao.atualizar(avaliacaoAtualizarDTO);
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
