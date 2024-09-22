package cronograma.api.model;

import cronograma.api.dto.AvaliacaoAtualizarDTO;
import cronograma.api.dto.AvaliacaoCadastrarDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "avaliacoes")
@Entity(name = "Avaliacao")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double nota;
    private String texto;
    private LocalDate data;
    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    public Avaliacao(AvaliacaoCadastrarDTO avaliacaoCadastrarDTO) {
        this.data = LocalDate.now();
        this.nota = avaliacaoCadastrarDTO.nota();
        this.texto = avaliacaoCadastrarDTO.texto();
    }

    public void atualizar(AvaliacaoAtualizarDTO avaliacaoAtualizarDTO) {
        if (avaliacaoAtualizarDTO.nota() != null) {
            this.nota = avaliacaoAtualizarDTO.nota();
        }
        if (avaliacaoAtualizarDTO.texto() != null) {
            this.texto = avaliacaoAtualizarDTO.texto();
        }
    }
}
