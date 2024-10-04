package cronograma.api.dto;

import cronograma.api.model.Cronograma;
import cronograma.api.model.Role;

public record CronogramaDetalhamentoDTO(
        Long id,
        String nome,
        String login,
        Role role
) {
    public CronogramaDetalhamentoDTO(Cronograma cronograma) {
        this(
                cronograma.getId(),
                cronograma.getNome(),
                cronograma.getLogin(),
                cronograma.getRole()
        );
    }
}
