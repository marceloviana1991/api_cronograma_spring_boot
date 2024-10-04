package cronograma.api.repository;

import cronograma.api.model.DiaDaSemana;
import cronograma.api.model.Evento;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findAllBycronogramaIdAndAtivoTrue(Long cronogramaId, Pageable pageable);

    List<Evento> findAllByAtivoTrue(Pageable pageable);

    Evento findByIdAndAtivoTrue(Long id);

    @Query("select e from Evento e " +
            "WHERE (e.horario <= :horario AND e.horarioTermina >= :horario AND e.diaDaSemana = :diaDaSemana) " +
            "OR (e.horario <= :horarioTermina AND e.horarioTermina >= :horarioTermina AND e.diaDaSemana = :diaDaSemana)")
    List<Evento> findByChoqueDeHorario(LocalTime horario, LocalTime horarioTermina, DiaDaSemana diaDaSemana);


    @Query("select e from Evento e " +
            "WHERE (e.horario <= :horario AND e.horarioTermina >= :horario AND e.diaDaSemana = :diaDaSemana AND e.id <> :id) " +
            "OR (e.horario <= :horarioTermina AND e.horarioTermina >= :horarioTermina AND e.diaDaSemana = :diaDaSemana AND e.id <> :id)")
    List<Evento> findByChoqueDeHorarioAtualizar(LocalTime horario, LocalTime horarioTermina, DiaDaSemana diaDaSemana,
                                                Long id);
}
