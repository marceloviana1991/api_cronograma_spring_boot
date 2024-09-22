package cronograma.api.dto;

import cronograma.api.model.Cronograma;

public record CronogramaListarDTO(Long id, String nome) {

    public CronogramaListarDTO(Cronograma cronograma) {
        this(cronograma.getId(), cronograma.getNome());
    }
}
