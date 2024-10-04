package cronograma.api.service;

import cronograma.api.repository.CronogramaRepository;
import cronograma.api.repository.EventoRepository;
import cronograma.api.dto.EventoAtualizarDTO;
import cronograma.api.dto.EventoCadastrarDTO;
import cronograma.api.infra.ValidacaoException;
import cronograma.api.model.Cronograma;
import cronograma.api.model.Evento;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private CronogramaRepository cronogramaRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private EventoValidacaoService eventoValidacaoService;

    public Evento instanciar(EventoCadastrarDTO eventoCadastrarDTO, HttpServletRequest request) {
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var cronograma = (Cronograma) cronogramaRepository.findByLogin(subject);
        eventoValidacaoService.validarInstancia(eventoCadastrarDTO);
        Evento evento = new Evento(eventoCadastrarDTO);
        evento.setCronograma(cronograma);
        return evento;
    }

    public Evento atualizar(EventoAtualizarDTO eventoAtualizarDTO, HttpServletRequest request) {
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var cronograma = (Cronograma) cronogramaRepository.findByLogin(subject);
        eventoValidacaoService.validarAtualizacao(eventoAtualizarDTO);
        Evento evento = eventoRepository.getReferenceById(eventoAtualizarDTO.id());
        if (cronograma.equals(evento.getCronograma())) {
            evento.atualizar(eventoAtualizarDTO);
            return evento;
        }
        throw new ValidacaoException("Atualização autorizada somente para usuário proprietário");
    }

    public Evento excluir(Long id,  HttpServletRequest request) {
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var cronograma = (Cronograma) cronogramaRepository.findByLogin(subject);
        Evento evento = eventoRepository.getReferenceById(id);
        if (cronograma.equals(evento.getCronograma())) {
            evento.excluir();
            return evento;
        }
        throw new ValidacaoException("Exclusão autorizada somente para usuário proprietário");
    }
}
