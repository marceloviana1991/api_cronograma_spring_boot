package cronograma.api.Service;

import cronograma.api.Repository.AvaliacaoRepository;
import cronograma.api.Repository.CronogramaRepository;
import cronograma.api.Repository.EventoRepository;
import cronograma.api.dto.AvaliacaoAtualizarDTO;
import cronograma.api.dto.AvaliacaoCadastrarDTO;
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
        Evento evento = eventoRepository.getReferenceById(avaliacaoCadastrarDTO.eventoId());
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
        return null;
    }
}
