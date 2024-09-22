package cronograma.api.controller;

import cronograma.api.Repository.AvaliacaoRepository;
import cronograma.api.Repository.EventoRepository;
import cronograma.api.dto.AvaliacaoCadastrarDTO;
import cronograma.api.model.Avaliacao;
import cronograma.api.model.Evento;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}