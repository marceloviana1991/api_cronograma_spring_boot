package cronograma.api.dto;

import cronograma.api.model.Avaliacao;

import java.time.LocalDate;

public record AvaliacaoListarDTO(
        double nota,
        String texto,
        LocalDate data) {

    public AvaliacaoListarDTO(Avaliacao avaliacao) {
        this(avaliacao.getNota(), avaliacao.getTexto(), avaliacao.getData());
    }

}
