package cronograma.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

public record EventoCadastrarDTO(
        @NotBlank
        String nome,
        @NotNull
        @Range(min=0, max=6)
        int diaDaSemana,
        @NotBlank
        @Pattern(regexp = "[0-2][0-9]:[0-5][0-9]")
        String horario,
        @NotBlank
        @Pattern(regexp = "[0-2][0-9]:[0-5][0-9]")
        String horarioTermina
) {
}
