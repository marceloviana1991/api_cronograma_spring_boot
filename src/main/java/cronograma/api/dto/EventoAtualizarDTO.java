package cronograma.api.dto;

import cronograma.api.model.DiaDaSemana;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record EventoAtualizarDTO(
        @NotNull
        Long id,
        String nome,
        DiaDaSemana diaDaSemana,
        LocalTime horario,
        LocalTime horarioTermina
) {
}
