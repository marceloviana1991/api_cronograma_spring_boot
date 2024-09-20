package cronograma.api.dto;

import cronograma.api.model.Cronograma;

public record CronogramaListarDTO(String nome) {

    public CronogramaListarDTO(Cronograma cronograma) {
        this(cronograma.getNome());
    }
}
