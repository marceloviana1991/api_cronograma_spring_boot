package cronograma.api.dto;

import cronograma.api.model.DiaDaSemana;
import cronograma.api.model.Evento;

import java.time.LocalTime;

public record EventoDetalhamentoDTO(
        Long id,
        String nome,
        DiaDaSemana diaDaSemana,
        LocalTime horario,
        LocalTime horarioTermina,
        Long cronogramaId
) {
    public EventoDetalhamentoDTO(Evento evento) {
        this(
                evento.getId(),
                evento.getNome(),
                evento.getDiaDaSemana(),
                evento.getHorario(),
                evento.getHorarioTermina(),
                evento.getCronograma().getId()
        );
    }
}
