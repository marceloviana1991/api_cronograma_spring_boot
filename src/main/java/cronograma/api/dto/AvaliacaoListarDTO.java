package cronograma.api.dto;

import cronograma.api.model.Avaliacao;

import java.time.LocalDate;

public record AvaliacaoListarDTO(
        Long id,
        double nota,
        String texto,
        LocalDate data,
        Long eventoId,
        Long cronogramaId) {

    public AvaliacaoListarDTO(Avaliacao avaliacao) {
        this(
                avaliacao.getId(),
                avaliacao.getNota(),
                avaliacao.getTexto(),
                avaliacao.getData(),
                avaliacao.getEvento().getId(),
                avaliacao.getCronograma().getId());
    }

}
