package cronograma.api.dto;

import cronograma.api.model.DiaDaSemana;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EventoDTO(
        @NotBlank
        String nome,
        @NotNull
        DiaDaSemana diaDaSemana,
        @NotBlank
        @Pattern(regexp = "[0-2][0-3]:[0-5][0-9]")
        String horario,
        @NotBlank
        @Pattern(regexp = "[0-2][0-3]:[0-5][0-9]")
        String horarioTermina) {
}
