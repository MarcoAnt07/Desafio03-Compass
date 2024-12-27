package io.github.marcoant07.ms_ticket_manager.controller;

import io.github.marcoant07.ms_ticket_manager.dto.EmailDTO;
import io.github.marcoant07.ms_ticket_manager.dto.TicketDTO;
import io.github.marcoant07.ms_ticket_manager.dto.TicketResponseDTO;
import io.github.marcoant07.ms_ticket_manager.dto.mapper.Mapper;
import io.github.marcoant07.ms_ticket_manager.entity.Email;
import io.github.marcoant07.ms_ticket_manager.entity.Event;
import io.github.marcoant07.ms_ticket_manager.entity.Ticket;
import io.github.marcoant07.ms_ticket_manager.repository.TicketRepository;
import io.github.marcoant07.ms_ticket_manager.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("ticket")
@Slf4j
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/create-ticket")
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDTO ticketDTO){

        Event event = fetchEventById(ticketDTO.getEventId());

        Ticket ticket = Mapper.toTicket(ticketDTO, event);

        Ticket savedTicket = ticketRepository.save(ticket);

        emailService.sendEmail(ticket);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedTicket);
    }

    @GetMapping("/get-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable("id") String id){
        Ticket ticket = ticketRepository.findTicketById(id);

        return ResponseEntity.status(HttpStatus.OK).body(Mapper.toTicketResponseDTO(ticket));
    }

    private Event fetchEventById(String eventId){

        String url = "http://localhost:8081/event/get-event/" + eventId;
        RestTemplate restTemplate = new RestTemplate();

        try{
            return restTemplate.getForObject(url, Event.class);
        } catch (Exception e){
            throw new RuntimeException("Event not found id: " + eventId);
        }
    }
}
