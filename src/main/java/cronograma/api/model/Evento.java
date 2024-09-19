package cronograma.api.model;

import cronograma.api.dto.EventoDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Table(name = "eventos")
@Entity(name = "Evento")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Evento {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private DiaDaSemana diaDaSemana;
    private LocalTime horario;

    public Evento(EventoDTO eventoDTO) {
        this.nome = eventoDTO.nome();
        this.diaDaSemana = eventoDTO.diaDaSemana();
        this.setHorario(eventoDTO.horario());
    }

    public void setHorario(String horario) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");
        this.horario = LocalTime.from(formatador.parse(horario));
    }
}
