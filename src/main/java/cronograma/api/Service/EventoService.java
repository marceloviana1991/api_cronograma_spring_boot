package cronograma.api.Service;

import cronograma.api.Repository.CronogramaRepository;
import cronograma.api.Repository.EventoRepository;
import cronograma.api.dto.EventoAtualizarDTO;
import cronograma.api.dto.EventoCadastrarDTO;
import cronograma.api.infra.ValidacaoException;
import cronograma.api.model.Cronograma;
import cronograma.api.model.DiaDaSemana;
import cronograma.api.model.Evento;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private CronogramaRepository cronogramaRepository;
    @Autowired
    private TokenService tokenService;

    public Evento instanciar(EventoCadastrarDTO eventoCadastrarDTO, HttpServletRequest request) {
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var cronograma = (Cronograma) cronogramaRepository.findByLogin(subject);
        var eventosCoqueDeHorario = eventoRepository.findByChoqueDeHorario(eventoCadastrarDTO.horario(),
                eventoCadastrarDTO.horarioTermina(),
                DiaDaSemana.fromString(eventoCadastrarDTO.diaDaSemana()));
        if (!eventosCoqueDeHorario.isEmpty()) {
            throw new ValidacaoException("Evento com choque de horário");
        }
        Evento evento = new Evento(eventoCadastrarDTO);
        evento.setCronograma(cronograma);
        return evento;
    }

    public Evento atualizar(EventoAtualizarDTO eventoAtualizarDTO, HttpServletRequest request) {
        var tokenJWT = tokenService.getToken(request);
        var subject = tokenService.getSubject(tokenJWT);
        var cronograma = (Cronograma) cronogramaRepository.findByLogin(subject);
        var eventosCoqueDeHorario = eventoRepository.findByChoqueDeHorarioAtualizar(eventoAtualizarDTO.horario(),
                eventoAtualizarDTO.horarioTermina(),
                eventoAtualizarDTO.diaDaSemana(),
                eventoAtualizarDTO.id());
        if (!eventosCoqueDeHorario.isEmpty()) {
            throw new ValidacaoException("Evento com choque de horário");
        }
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
