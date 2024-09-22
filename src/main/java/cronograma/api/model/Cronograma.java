package cronograma.api.model;

import cronograma.api.dto.CronogramaAtualizarDTO;
import cronograma.api.dto.CronogramaCadastrarDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "cronogramas")
@Entity(name = "Cronograma")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cronograma {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    public Cronograma(CronogramaCadastrarDTO cronogramaCadastrarDTO) {
        this.nome = cronogramaCadastrarDTO.nome();
    }

    public void atualizar(CronogramaAtualizarDTO cronogramaAtualizarDTO) {
        if (cronogramaAtualizarDTO.nome() != null) {
            this.nome = cronogramaAtualizarDTO.nome();
        }
    }
}
