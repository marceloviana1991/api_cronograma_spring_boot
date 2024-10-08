package cronograma.api.service;

import cronograma.api.repository.AvaliacaoRepository;
import cronograma.api.repository.CronogramaRepository;
import cronograma.api.repository.EventoRepository;
import cronograma.api.dto.AvaliacaoAtualizarDTO;
import cronograma.api.dto.AvaliacaoCadastrarDTO;
import cronograma.api.infra.ValidacaoException;
import cronograma.api.model.Avaliacao;
import cronograma.api.model.Cronograma;
import cronograma.api.model.Evento;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private CronogramaRepository cronogramaRepository;
    @Autowired
    private TokenService tokenService;

    public Avaliacao instaciar(AvaliacaoCadastrarDTO avaliacaoCadastrarDTO, HttpServletRequest request) {
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var cronograma = (Cronograma) cronogramaRepository.findByLogin(subject);
        if (!eventoRepository.existsById(avaliacaoCadastrarDTO.eventoId())) {
            throw new ValidacaoException("Id do evento informado não existe");
        }
        Evento evento = eventoRepository.getReferenceById(avaliacaoCadastrarDTO.eventoId());
        if (!evento.isAtivo()) {
            throw new ValidacaoException("Id do evento informado foi desativado");
        }
        Avaliacao avaliacao = new Avaliacao(avaliacaoCadastrarDTO);
        avaliacao.setCronograma(cronograma);
        avaliacao.setEvento(evento);
        return avaliacao;
    }

    public Avaliacao atualizar(AvaliacaoAtualizarDTO avaliacaoAtualizarDTO, HttpServletRequest request) {
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var cronograma = (Cronograma) cronogramaRepository.findByLogin(subject);
        Avaliacao avaliacao = avaliacaoRepository.getReferenceById(avaliacaoAtualizarDTO.id());
        if (cronograma.equals(avaliacao.getCronograma())) {
            avaliacao.atualizar(avaliacaoAtualizarDTO);
            return avaliacao;
        }
        throw new ValidacaoException("Atualização autorizada somente para usuário proprietário");
    }
}
