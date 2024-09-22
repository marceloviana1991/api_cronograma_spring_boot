package cronograma.api.controller;

import cronograma.api.Repository.AvaliacaoRepository;
import cronograma.api.Repository.EventoRepository;
import cronograma.api.dto.AvaliacaoCadastrarDTO;
import cronograma.api.dto.AvaliacaoListarDTO;
import cronograma.api.model.Avaliacao;
import cronograma.api.model.Evento;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoControler {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private EventoRepository eventoRepository;

    @PostMapping
    @Transactional
    public void cadastrarAvaliacao(@RequestBody @Valid AvaliacaoCadastrarDTO avaliacaoCadastrarDTO) {
        Optional<Evento> eventoOptional = eventoRepository.findById(avaliacaoCadastrarDTO.eventoId());
        if (eventoOptional.isPresent()) {
            Avaliacao avaliacao = new Avaliacao(avaliacaoCadastrarDTO);
            avaliacao.setEvento(eventoOptional.get());
            avaliacaoRepository.save(avaliacao);
        }
    }

    @GetMapping
    public List<AvaliacaoListarDTO> listarAvaliacoes(
            @RequestParam(value = "eventoId", required = false) Long eventoId,
            @PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (eventoId != null) {
            return avaliacaoRepository.findByeventoId(eventoId, pageable).stream().map(AvaliacaoListarDTO::new).toList();
        }
        return avaliacaoRepository.findAll(pageable).stream().map(AvaliacaoListarDTO::new).toList();
    }
}
