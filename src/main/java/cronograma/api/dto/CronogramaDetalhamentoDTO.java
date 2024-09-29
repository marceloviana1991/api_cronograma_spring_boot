package cronograma.api.dto;

import cronograma.api.model.Cronograma;

public record CronogramaDetalhamentoDTO(
        Long id,
        String nome
) {
    public CronogramaDetalhamentoDTO(Cronograma cronograma) {
        this(
                cronograma.getId(),
                cronograma.getNome()
        );
    }
}
