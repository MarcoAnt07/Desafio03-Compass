package io.github.marcoant07.ms_ticket_manager.controller;

import io.github.marcoant07.ms_ticket_manager.dto.TicketDTO;
import io.github.marcoant07.ms_ticket_manager.dto.TicketResponseDTO;
import io.github.marcoant07.ms_ticket_manager.dto.mapper.Mapper;
import io.github.marcoant07.ms_ticket_manager.entity.Event;
import io.github.marcoant07.ms_ticket_manager.entity.Ticket;
import io.github.marcoant07.ms_ticket_manager.repository.TicketRepository;
import io.github.marcoant07.ms_ticket_manager.services.EmailService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TicketControlletUnit {

    @InjectMocks
    private TicketController ticketController;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private EmailService emailService;

    @Test
    void createTicket_ReturnTicket(){
        TicketDTO ticketDTO = new TicketDTO("1A", "Aa", "000.000.000-00", "676b04511797cb53fe6a00b5", "aa@aa.com", 600.0, 100.0);
        Event event = new Event("676b04511797cb53fe6a00b5", "Cc", LocalDateTime.parse("2024-12-31T00:00:00"), "60311-310", "Rua Santa Inês", "Pirambu", "Fortaleza", "CE");
        Ticket savedTicket = new Ticket("1A", "Aa", "000.000.000-00", "aa@aa.com", event, 600.0, 100.0, false);

        Mockito.when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(savedTicket);

        ResponseEntity<Ticket> response = ticketController.createTicket(ticketDTO);

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(201);
        Assertions.assertThat(response.getBody()).isNotNull();
        Mockito.verify(emailService, Mockito.times(1)).sendEmail(Mockito.any(Ticket.class));
    }

    @Test
    void getTicketById_ReturnTicket(){
        String ticketId = "1A";
        Event event = new Event("676b04511797cb53fe6a00b5", "Cc", LocalDateTime.parse("2024-12-31T00:00:00"), "60311-310", "Rua Santa Inês", "Pirambu", "Fortaleza", "CE");
        Ticket ticket = new Ticket(ticketId, "Aa", "000.000.000-00", "aa@aa.com", event, 600.0, 100.0, false);

        Mockito.when(ticketRepository.findActiveTicketById(Mockito.anyString())).thenReturn(ticket);

        ResponseEntity<TicketResponseDTO> response = ticketController.getTicketById(ticketId);

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getEvent()).isEqualTo(event);
        Assertions.assertThat(response.getBody().getCostumerName()).isEqualTo(ticket.getCostumerName());
    }

    @Test
    void checkTicketsByEvent_ReturnListOfTickets(){
        Event event = new Event("676b04511797cb53fe6a00b5", "Cc", LocalDateTime.parse("2024-12-31T00:00:00"), "60311-310", "Rua Santa Inês", "Pirambu", "Fortaleza", "CE");

        List<Ticket> listTickets = List.of(new Ticket("1A", "Aa", "000.000.000-00", "aa@aa.com", event, 600.0, 100.0, false),
                                            new Ticket("2A", "Bb", "000.000.000-00", "bb@bb.com", event, 600.0, 100.0, false),
                                            new Ticket("3A", "Aa", "000.000.000-00", "aa@aa.com", event, 600.0, 100.0, false));

        Mockito.when(ticketRepository.findTicketByEventId(event.getId())).thenReturn(listTickets);

        ResponseEntity<List<TicketDTO>> response = ticketController.checkTicketsByEvent(event.getId());

        System.out.println(response);

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody()).isNotEmpty();
        Assertions.assertThat(response.getBody().size()).isEqualTo(listTickets.size());
        Assertions.assertThat(response.getBody().get(0).getCostumerName()).isEqualTo("Aa");
        Assertions.assertThat(response.getBody().get(1).getCustumerMail()).isEqualTo("bb@bb.com");
    }

    @Test
    void checkTicketsByEvent_ReturnStatus404(){
        Event event = new Event("676d9722107c4a0ccd7f5af8", "Cc", LocalDateTime.parse("2024-12-31T00:00:00"), "60311-310", "Rua Santa Inês", "Pirambu", "Fortaleza", "CE");

        Mockito.when(ticketRepository.findTicketByEventId(event.getId())).thenReturn(List.of());

        ResponseEntity<List<TicketDTO>> response = ticketController.checkTicketsByEvent(event.getId());

        System.out.println(response);

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(404);
        Assertions.assertThat(response.getBody()).isNull();
    }

    @Test
    void updateTicket_ReturnTicket(){
        TicketDTO ticketDTO = new TicketDTO("1A", "Aa", "000.000.000-00", "676b04511797cb53fe6a00b5", "aa@aa.com", 600.0, 100.0);
        Event event = new Event("676b04511797cb53fe6a00b5", "Cc", LocalDateTime.parse("2024-12-31T00:00:00"), "60311-310", "Rua Santa Inês", "Pirambu", "Fortaleza", "CE");
        Ticket savedTicket = new Ticket("1A", "Aa", "000.000.000-00", "aa@aa.com", event, 600.0, 100.0, false);

        Mockito.when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(savedTicket);

        ResponseEntity<Ticket> response = ticketController.updateTicketById("1A",ticketDTO);

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getBody()).isNotNull();
        Mockito.verify(emailService, Mockito.times(1)).sendEmailUpdate(Mockito.any(Ticket.class));
    }

    @Test
    void deleteTicket_ReturnStatus200(){
        String ticketId = "1A";
        Event event = new Event("676d9722107c4a0ccd7f5af8", "Cc", LocalDateTime.parse("2024-12-31T00:00:00"), "60311-310", "Rua Santa Inês", "Pirambu", "Fortaleza", "CE");
        Ticket ticket = new Ticket(ticketId, "Aa", "000.000.000-00", "aa@aa.com", event, 600.0, 100.0, false);

        Mockito.when(ticketRepository.findActiveTicketById(ticketId)).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.deleteTicketById(ticketId);

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getBody().getDeleted()).isTrue();
        Mockito.verify(ticketRepository).save(Mockito.any(Ticket.class));
    }

    @Test
    void deleteTicket_ReturnStatus404(){
        String ticketId = "1A";

        Mockito.when(ticketRepository.findActiveTicketById(ticketId)).thenReturn(null);

        ResponseEntity<Ticket> response = ticketController.deleteTicketById(ticketId);

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(404);
        Assertions.assertThat(response.getBody()).isNull();
        Mockito.verify(ticketRepository, Mockito.never()).save(Mockito.any(Ticket.class));
    }
}
