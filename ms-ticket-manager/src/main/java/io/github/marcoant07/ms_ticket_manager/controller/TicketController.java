package io.github.marcoant07.ms_ticket_manager.controller;

import io.github.marcoant07.ms_ticket_manager.dto.TicketDTO;
import io.github.marcoant07.ms_ticket_manager.dto.TicketResponseDTO;
import io.github.marcoant07.ms_ticket_manager.dto.mapper.Mapper;
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

@RestController
@RequestMapping("api/v1")
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

    @PutMapping("/update-ticket/{id}")
    public ResponseEntity<Ticket> updateTicketById(@PathVariable("id") String id, @RequestBody TicketDTO ticketDTO){

        Ticket ticket = ticketRepository.findTicketById(id);
        Event event = fetchEventById(ticketDTO.getEventId());
        ticket = Mapper.toTicket(ticketDTO, event);
        Ticket savedTicket = ticketRepository.save(ticket);

        emailService.sendEmailUpdate(ticket);

        return ResponseEntity.status(HttpStatus.OK).body(savedTicket);
    }

    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<Void> deleteTicketById(@PathVariable("id") String id){

        Ticket ticket = ticketRepository.findActiveTicketById(id);

        if(ticket == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ticket.setDeleted(true);
        ticketRepository.save(ticket);

        return ResponseEntity.status(HttpStatus.OK).build();
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
