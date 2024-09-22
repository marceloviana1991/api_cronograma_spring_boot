package cronograma.api.dto;

import cronograma.api.model.DiaDaSemana;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EventoAtualizarDTO(
        @NotNull
        Long id,
        String nome,
        DiaDaSemana diaDaSemana,
        @Pattern(regexp = "[0-2][0-9]:[0-5][0-9]")
        String horario,
        @Pattern(regexp = "[0-2][0-9]:[0-5][0-9]")
        String horarioTermina
) {
}
