package cronograma.api.dto;

import jakarta.validation.constraints.NotNull;

public record AvaliacaoAtualizarDTO(
        @NotNull
        Long id,
        Double nota,
        String texto
) {
}
