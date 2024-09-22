package cronograma.api.model;

import cronograma.api.dto.EventoAtualizarDTO;
import cronograma.api.dto.EventoCadastrarDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Table(name = "eventos")
@Entity(name = "Evento")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Evento {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Enumerated(EnumType.ORDINAL)
    private DiaDaSemana diaDaSemana;
    private LocalTime horario;
    private LocalTime horarioTermina;
    private boolean ativo;
    @ManyToOne
    @JoinColumn(name = "cronograma_id")
    private Cronograma cronograma;
    static private final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("HH:mm");

    public Evento(EventoCadastrarDTO eventoCadastrarDTO) {
        this.nome = eventoCadastrarDTO.nome();
        this.diaDaSemana = eventoCadastrarDTO.diaDaSemana();
        this.setHorario(eventoCadastrarDTO.horario());
        this.setHorarioTermina(eventoCadastrarDTO.horarioTermina());
        this.ativo = true;
    }

    public void setHorario(String horario) {
        this.horario = LocalTime.from(formatador.parse(horario));
    }

    public void setHorarioTermina(String horarioTermina) {
        this.horarioTermina = LocalTime.from(formatador.parse(horarioTermina));
    }

    public void atualizar(EventoAtualizarDTO eventoAtualizarDTO) {
        if (eventoAtualizarDTO.nome() != null) {
            this.nome = eventoAtualizarDTO.nome();
        }
        if (eventoAtualizarDTO.diaDaSemana() != null) {
            this.diaDaSemana = eventoAtualizarDTO.diaDaSemana();
        }
        if (eventoAtualizarDTO.horario() != null) {
            this.setHorario(eventoAtualizarDTO.horario());
        }
        if (eventoAtualizarDTO.horarioTermina() != null) {
            this.setHorarioTermina(eventoAtualizarDTO.horarioTermina());
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
