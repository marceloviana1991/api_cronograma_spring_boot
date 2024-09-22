package cronograma.api.dto;

import jakarta.validation.constraints.NotNull;

public record CronogramaAtualizarDTO(
        @NotNull
        Long id,
        String nome
) {
}
