package cronograma.api.dto;

import cronograma.api.model.DiaDaSemana;

public record EventoDTO(String nome, DiaDaSemana diaDaSemana, String horario) {
}
