package cronograma.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AvaliacaoCadastrarDTO(
        @NotNull
        double nota,
        @NotBlank
        String texto,
        @NotNull
        Long eventoId
) {
}
