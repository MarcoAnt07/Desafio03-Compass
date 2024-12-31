package io.github.marcoant07.ms_event_manager.controller;

import io.github.marcoant07.ms_event_manager.dto.EventDTO;
import io.github.marcoant07.ms_event_manager.dto.GetEventDTO;
import io.github.marcoant07.ms_event_manager.dto.mapper.Mapper;
import io.github.marcoant07.ms_event_manager.entity.Event;
import io.github.marcoant07.ms_event_manager.entity.Ticket;
import io.github.marcoant07.ms_event_manager.exception.throwable.ConflictException;
import io.github.marcoant07.ms_event_manager.exception.throwable.NotFoundException;
import io.github.marcoant07.ms_event_manager.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class EventControllerUnit {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void createEvent_WithValidData_ReturnEvent(){
        EventDTO eventDTO = new EventDTO("Hhhhh", LocalDateTime.parse("2024-12-30T21:00:00"), "60326-515");

        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(Mapper.toEvent(eventDTO));

        ResponseEntity<Event> response = eventController.createEvent(eventDTO);

        System.out.println(response);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getAllEvents_ReturnListOfEvents(){
        List<Event> mockEvents = List.of(new Event("1A", "Aa", LocalDateTime.parse("2024-12-30T21:00:00"), "60326-515", "Avenida Sargento Hermínio Sampaio", "Monte Castelo", "Fortaleza", "CE"),
                                         new Event("2A", "Bb", LocalDateTime.parse("2024-12-30T21:00:00"), "60326-515", "Avenida Sargento Hermínio Sampaio", "Monte Castelo", "Fortaleza", "CE"),
                                         new Event("3A", "Cc", LocalDateTime.parse("2024-12-30T21:00:00"), "60326-515", "Avenida Sargento Hermínio Sampaio", "Monte Castelo", "Fortaleza", "CE"));

        Mockito.when(eventRepository.findAll()).thenReturn(mockEvents);

        ResponseEntity<List<GetEventDTO>> response = eventController.getAllEvents();

        System.out.println(response);

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody()).hasSize(mockEvents.size());
        Assertions.assertThat(response.getBody()).extracting(GetEventDTO::getNameEvent).containsExactly("Aa", "Bb", "Cc");
    }

    @Test
    void getAllEventsSorted_ReturnListOfEventsSorted(){
        List<Event> mockEvents = List.of(new Event("1A", "Cc", LocalDateTime.parse("2024-12-30T21:00:00"), "60326-515", "Avenida Sargento Hermínio Sampaio", "Monte Castelo", "Fortaleza", "CE"),
                new Event("2A", "Bb", LocalDateTime.parse("2024-12-30T21:00:00"), "60326-515", "Avenida Sargento Hermínio Sampaio", "Monte Castelo", "Fortaleza", "CE"),
                new Event("3A", "Aa", LocalDateTime.parse("2024-12-30T21:00:00"), "60326-515", "Avenida Sargento Hermínio Sampaio", "Monte Castelo", "Fortaleza", "CE"));

        Mockito.when(eventRepository.findAll(Sort.by(Sort.Direction.ASC, "eventName"))).thenReturn(mockEvents);

        ResponseEntity<List<GetEventDTO>> response = eventController.getAllEventsSorted();

        System.out.println(response);

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody()).hasSize(mockEvents.size());
        Assertions.assertThat(response.getBody()).extracting(GetEventDTO::getNameEvent).containsExactly("Cc", "Bb", "Aa");
    }

    @Test
    void getEventById_ReturnEvent(){
        Event mockEvent = new Event("1A", "Cc", LocalDateTime.parse("2024-12-30T21:00:00"), "60326-515", "Avenida Sargento Hermínio Sampaio", "Monte Castelo", "Fortaleza", "CE");

        Mockito.when(eventRepository.findById(Mockito.any(String.class))).thenReturn(Optional.of(mockEvent));

        ResponseEntity<GetEventDTO> response = eventController.getById("1A");

        System.out.println(response);

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getNameEvent()).isEqualTo(mockEvent.getEventName());
        Assertions.assertThat(response.getBody().getDateTime()).isEqualTo(mockEvent.getDateTime());
        Assertions.assertThat(response.getBody().getLogradouro()).isEqualTo(mockEvent.getLogradouro());
        Assertions.assertThat(response.getBody().getCep()).isEqualTo(mockEvent.getCep());
        Assertions.assertThat(response.getBody().getBairro()).isEqualTo(mockEvent.getBairro());
        Assertions.assertThat(response.getBody().getCidade()).isEqualTo(mockEvent.getCidade());
        Assertions.assertThat(response.getBody().getUf()).isEqualTo(mockEvent.getUf());
    }

    @Test
    void getEventById_ReturnStatus404(){
        String eventId = "1A";

        Mockito.when(eventRepository.findById(Mockito.any(String.class))).thenReturn(Optional.empty());

        NotFoundException notFoundException = Assertions.catchThrowableOfType(() -> eventController.getById(eventId), NotFoundException.class);

        Assertions.assertThat(notFoundException).isNotNull();
        Assertions.assertThat(notFoundException.getMessage()).isEqualTo("Event not found");
    }

    @Test
    void updateEvent_ReturnEvent(){
        String eventId = "1A";
        EventDTO eventDTO = new EventDTO("Updated Event", LocalDateTime.parse("2024-12-30T12:00:00"), "60326-515");
        Event existingEvent = new Event("1A", "Cc", LocalDateTime.parse("2024-12-30T21:00:00"), "60311-310", "Rua Santa Inês", "Pirambu", "Fortaleza", "CE");
        Event updatedEvent = new Event("1A", "Updated Event", LocalDateTime.parse("2024-12-30T12:00:00"), "60326-515", "Avenida Sargento Hermínio Sampaio", "Monte Castelo", "Fortaleza", "CE");

        Mockito.when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(updatedEvent);

        ResponseEntity<Event> response = eventController.updateEventById(eventId, eventDTO);

        System.out.println(response);

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getEventName()).isEqualTo("Updated Event");
        Assertions.assertThat(response.getBody().getDateTime()).isEqualTo(LocalDateTime.parse("2024-12-30T12:00:00"));
    }

    @Test
    void updateEvent_WithAssociatedTicket_ReturnStatus409(){
        String eventId = "676b04511797cb53fe6a00b5";
        EventDTO eventDTO = new EventDTO("Updated Event", LocalDateTime.parse("2024-12-30T12:00:00"), "60326-515");
        Event existingEvent = new Event("676b04511797cb53fe6a00b5", "Cc", LocalDateTime.parse("2024-12-30T21:00:00"), "60311-310", "Rua Santa Inês", "Pirambu", "Fortaleza", "CE");
        Event updatedEvent = new Event("676b04511797cb53fe6a00b5", "Updated Event", LocalDateTime.parse("2024-12-30T12:00:00"), "60326-515", "Avenida Sargento Hermínio Sampaio", "Monte Castelo", "Fortaleza", "CE");

        Ticket ticket = new Ticket("1B", "Aa", "000.000.000-00", "Aa@aa.com", "676b04511797cb53fe6a00b5", 600.0, 100.0, false);

        ParameterizedTypeReference<List<Ticket>> typeReference = new ParameterizedTypeReference<>() {};

        Mockito.lenient().when(restTemplate.exchange(
                Mockito.eq("http://localhost:8081/api/v1/check-tickets-by-event/" + eventId),
                Mockito.eq(HttpMethod.GET),
                Mockito.isNull(),
                Mockito.eq(typeReference)
        )).thenReturn(new ResponseEntity<>(List.of(ticket), HttpStatus.OK));

        ConflictException conflictException = Assertions.catchThrowableOfType( () -> eventController.updateEventById(eventId, eventDTO), ConflictException.class);

        Assertions.assertThat(conflictException.getMessage()).isEqualTo("There are tickets registered for this event");
        Assertions.assertThat(conflictException).isNotNull();
    }

    @Test
    void updateEvent_ReturnStatus404(){
        String eventId = "676b04511797cb53fe6a0000";
        EventDTO eventDTO = new EventDTO("Updated Event", LocalDateTime.parse("2024-12-30T12:00:00"), "60326-515");

        Mockito.when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        NotFoundException notFoundException = Assertions.catchThrowableOfType(() -> eventController.updateEventById(eventId, eventDTO), NotFoundException.class);

        Assertions.assertThat(notFoundException).isNotNull();
        Assertions.assertThat(notFoundException.getMessage()).isEqualTo("Entity not found");
    }

    @Test
    void deleteEvent_ReturnStatus200(){
        String eventId = "1A";
        Event event = new Event("1A", "Cc", LocalDateTime.parse("2024-12-30T21:00:00"), "60311-310", "Rua Santa Inês", "Pirambu", "Fortaleza", "CE");

        Mockito.when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        ResponseEntity<Void> response = eventController.deletePostById(eventId);

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void deleteEvent_WithAssociatedTicket_ReturnStatus409(){
        String eventId = "676b04511797cb53fe6a00b5";
        Event event = new Event("676b04511797cb53fe6a00b5", "Cc", LocalDateTime.parse("2024-12-30T21:00:00"), "60311-310", "Rua Santa Inês", "Pirambu", "Fortaleza", "CE");
        Ticket ticket = new Ticket("1B", "Aa", "000.000.000-00", "Aa@aa.com", "676b04511797cb53fe6a00b5", 600.0, 100.0, false);

        Mockito.when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        ParameterizedTypeReference<List<Ticket>> typeReference = new ParameterizedTypeReference<>() {};

        Mockito.lenient().when(restTemplate.exchange(
                Mockito.eq("http://localhost:8081/api/v1/check-tickets-by-event/" + eventId),
                Mockito.eq(HttpMethod.GET),
                Mockito.isNull(),
                Mockito.eq(typeReference)
        )).thenReturn(new ResponseEntity<>(List.of(ticket), HttpStatus.OK));

        ConflictException conflictException = Assertions.catchThrowableOfType( () -> eventController.deletePostById(eventId), ConflictException.class);

        Assertions.assertThat(conflictException).isNotNull();
        Assertions.assertThat(conflictException.getMessage()).isEqualTo("Conflict - Tickets linked to this event");
    }
}
