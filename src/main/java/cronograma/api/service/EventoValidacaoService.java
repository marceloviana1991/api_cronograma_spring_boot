package cronograma.api.service;

import cronograma.api.repository.EventoRepository;
import cronograma.api.dto.EventoAtualizarDTO;
import cronograma.api.dto.EventoCadastrarDTO;
import cronograma.api.infra.ValidacaoException;
import cronograma.api.model.DiaDaSemana;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventoValidacaoService {

    @Autowired
    private EventoRepository eventoRepository;

    public void validarInstancia(EventoCadastrarDTO eventoCadastrarDTO) {
        var eventosCoqueDeHorario = eventoRepository.findByChoqueDeHorario(eventoCadastrarDTO.horario(),
                eventoCadastrarDTO.horarioTermina(),
                DiaDaSemana.fromString(eventoCadastrarDTO.diaDaSemana()));
        if (!eventosCoqueDeHorario.isEmpty()) {
            throw new ValidacaoException("Evento com choque de horário");
        }
    }

    public void validarAtualizacao(EventoAtualizarDTO eventoAtualizarDTO) {
        var eventosCoqueDeHorario = eventoRepository.findByChoqueDeHorarioAtualizar(eventoAtualizarDTO.horario(),
                eventoAtualizarDTO.horarioTermina(),
                eventoAtualizarDTO.diaDaSemana(),
                eventoAtualizarDTO.id());
        if (!eventosCoqueDeHorario.isEmpty()) {
            throw new ValidacaoException("Evento com choque de horário");
        }
    }
}
