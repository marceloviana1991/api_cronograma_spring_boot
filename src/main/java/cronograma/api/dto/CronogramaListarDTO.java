package cronograma.api.dto;

import cronograma.api.model.Cronograma;

public record CronogramaListarDTO(Long id, String nome, String login) {

    public CronogramaListarDTO(Cronograma cronograma) {
        this(
                cronograma.getId(),
                cronograma.getNome(),
                cronograma.getLogin());
    }
}
