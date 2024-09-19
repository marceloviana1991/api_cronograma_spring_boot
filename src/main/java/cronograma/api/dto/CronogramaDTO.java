package cronograma.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CronogramaDTO(
        @NotBlank
        String nome) {
}
