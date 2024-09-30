package cronograma.api.Repository;

import cronograma.api.model.Evento;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findAllBycronogramaIdAndAtivoTrue(Long cronogramaId, Pageable pageable);

    List<Evento> findAllByAtivoTrue(Pageable pageable);

    Evento findByIdAndAtivoTrue(Long id);
}
