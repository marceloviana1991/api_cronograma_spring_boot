package cronograma.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CronogramaCadastrarDTO(
        @NotBlank
        String nome
){
}
