package cronograma.api.dto;

public record AvaliacaoCadastrarDTO(
        double nota,
        String texto,
        Long eventoId
) {
}
