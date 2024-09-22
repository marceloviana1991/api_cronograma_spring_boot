package cronograma.api.Repository;

import cronograma.api.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    public List<Evento> findBycronogramaId(Long id);
}
