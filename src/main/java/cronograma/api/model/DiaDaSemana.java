package cronograma.api.model;

public enum DiaDaSemana {
    SEGUNDA(0),
    TERCA(1),
    QUARTA(2),
    QUINTA(3),
    SEXTA(4),
    SABADO(5),
    DOMINGO(6);

    private int ordem;
    DiaDaSemana(int ordem) {
        this.ordem = ordem;
    }

    public static DiaDaSemana fromString(int ordem) {
        for (DiaDaSemana diaDaSemana : DiaDaSemana.values()) {
            if (diaDaSemana.ordem == ordem) {
                return diaDaSemana;
            }
        }
        throw new IllegalArgumentException("Argumento inválido!");
    }
}
