package cronograma.api.Repository;

import cronograma.api.model.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    Page<Evento> findAllBycronogramaIdAndAtivoTrue(Long id, Pageable pageable);

    Page<Evento> findAllByAtivoTrue(Pageable pageable);
}
