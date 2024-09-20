package cronograma.api.dto;

import cronograma.api.model.DiaDaSemana;
import cronograma.api.model.Evento;

import java.time.LocalTime;

public record EventoListarDTO(String nome,
                              DiaDaSemana diaDaSemana,
                              LocalTime horario,
                              LocalTime horarioTermina) {

    public EventoListarDTO(Evento evento) {
        this(evento.getNome(),
                evento.getDiaDaSemana(),
                evento.getHorario(),
                evento.getHorarioTermina());
    }
}
