package cronograma.api.repository;

import cronograma.api.model.Avaliacao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    public List<Avaliacao> findByeventoId(Long id, Pageable pageable);

    public List<Avaliacao> findByEventoAtivoTrue(Pageable pageable);
}
