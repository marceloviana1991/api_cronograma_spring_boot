package cronograma.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.time.LocalTime;

public record EventoCadastrarDTO(
        @NotBlank
        String nome,
        @NotNull
        @Range(min=0, max=6)
        int diaDaSemana,
        @NotNull
        LocalTime horario,
        @NotNull
        LocalTime horarioTermina
) {
}
