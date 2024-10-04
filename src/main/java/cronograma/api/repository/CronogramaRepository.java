package cronograma.api.repository;

import cronograma.api.model.Cronograma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface CronogramaRepository extends JpaRepository<Cronograma, Long> {

    UserDetails findByLogin(String login);
}
