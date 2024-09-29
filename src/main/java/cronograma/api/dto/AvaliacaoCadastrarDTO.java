package cronograma.api.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AvaliacaoCadastrarDTO(
        @NotNull
        @DecimalMax("5.0") @DecimalMin("0.0")
        double nota,
        @NotBlank
        String texto,
        @NotNull
        Long eventoId
) {
}
