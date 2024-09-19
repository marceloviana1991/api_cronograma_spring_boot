package cronograma.api.model;

import cronograma.api.dto.CronogramaDTO;
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

    public Cronograma(CronogramaDTO cronogramaDTO) {
        this.nome = cronogramaDTO.nome();
    }
}
