package io.github.marcoant07.ms_ticket_manager.controller;

import io.github.marcoant07.ms_ticket_manager.dto.TicketDTO;
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
}
