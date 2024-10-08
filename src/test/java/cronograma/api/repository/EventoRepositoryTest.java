package cronograma.api.repository;

import cronograma.api.dto.EventoAtualizarDTO;
import cronograma.api.dto.EventoCadastrarDTO;
import cronograma.api.model.DiaDaSemana;
import cronograma.api.model.Evento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class EventoRepositoryTest {

    @Autowired
    private EventoRepository eventoRepository;

    @Test
    @DisplayName("Deveria devolver vazio quando evento a cadastrar não possui choque de horário com outros eventos já " +
            "cadastrados")
    void findByChoqueDeHorarioHorarioIgualDiaDiferente() {
        EventoCadastrarDTO eventoCadastrarDTO = new EventoCadastrarDTO("evento", 1,
                LocalTime.of(12,0), LocalTime.of(13,0));
        List<Evento> evento = eventoRepository.findByChoqueDeHorario(eventoCadastrarDTO.horario(),
                eventoCadastrarDTO.horarioTermina(),
                DiaDaSemana.fromString(eventoCadastrarDTO.diaDaSemana()));
        assertTrue(evento.isEmpty());
    }
    @Test
    @DisplayName("Deveria devolver vazio quando evento a cadastrar não possui choque de horário com outros eventos já " +
            "cadastrados")
    void findByChoqueDeHorarioHorarioDiferenteDiaIgual() {
        EventoCadastrarDTO eventoCadastrarDTO = new EventoCadastrarDTO("evento", 0,
                LocalTime.of(13,1), LocalTime.of(14,0));
        List<Evento> evento = eventoRepository.findByChoqueDeHorario(eventoCadastrarDTO.horario(),
                eventoCadastrarDTO.horarioTermina(),
                DiaDaSemana.fromString(eventoCadastrarDTO.diaDaSemana()));
        assertTrue(evento.isEmpty());
    }

    @Test
    @DisplayName("Deveria devolver vazio quando evento a atualizar não possui choque de horário com outros eventos " +
            "diferentes dele mesmo")
    void findByChoqueDeHorarioAtualizarChoqueDeHorarioNoProprioEvento() {
        EventoAtualizarDTO eventoAtualizarDTO = new EventoAtualizarDTO(1L, "evento", DiaDaSemana.SEGUNDA,
                LocalTime.of(12,30), LocalTime.of(13,30));
        List<Evento> evento = eventoRepository.findByChoqueDeHorarioAtualizar(eventoAtualizarDTO.horario(),
                eventoAtualizarDTO.horarioTermina(),
                eventoAtualizarDTO.diaDaSemana(),
                eventoAtualizarDTO.id());
        assertTrue(evento.isEmpty());
    }
}