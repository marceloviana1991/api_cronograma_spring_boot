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
    private LocalTime horarioTermina;

    public Evento(EventoDTO eventoDTO) {
        this.nome = eventoDTO.nome();
        this.diaDaSemana = eventoDTO.diaDaSemana();
        this.setHorario(eventoDTO.horario());
        this.setHorarioTermina(eventoDTO.horarioTermina());
    }

    public void setHorario(String horario) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");
        this.horario = LocalTime.from(formatador.parse(horario));
    }

    public void setHorarioTermina(String horarioTermina) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");
        this.horarioTermina = LocalTime.from(formatador.parse(horarioTermina));
    }
}
