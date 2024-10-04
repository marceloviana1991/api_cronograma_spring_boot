package cronograma.api.dto;

import cronograma.api.model.Cronograma;
import cronograma.api.model.Role;

public record CronogramaListarDTO(Long id, String nome, String login, Role role) {

    public CronogramaListarDTO(Cronograma cronograma) {
        this(
                cronograma.getId(),
                cronograma.getNome(),
                cronograma.getLogin(),
                cronograma.getRole());
    }
}
