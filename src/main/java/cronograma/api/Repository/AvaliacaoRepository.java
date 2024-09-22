package cronograma.api.Repository;

import cronograma.api.model.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    public Page<Avaliacao> findByeventoId(Long id, Pageable pageable);
}
