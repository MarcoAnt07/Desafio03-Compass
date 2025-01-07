package io.github.marcoant07.ms_ticket_manager.controller;

import io.github.marcoant07.ms_ticket_manager.dto.TicketDTO;
import io.github.marcoant07.ms_ticket_manager.dto.TicketResponseDTO;
import io.github.marcoant07.ms_ticket_manager.dto.mapper.Mapper;
import io.github.marcoant07.ms_ticket_manager.entity.Event;
import io.github.marcoant07.ms_ticket_manager.entity.Ticket;
import io.github.marcoant07.ms_ticket_manager.exception.throwable.NotFoundException;
import io.github.marcoant07.ms_ticket_manager.repository.TicketRepository;
import io.github.marcoant07.ms_ticket_manager.services.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
@Slf4j
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EmailService emailService;

    @Operation(summary = "Create a new ticket", responses = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Ticket created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class)
                    )
            )
    })
    @PostMapping("/create-ticket")
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDTO ticketDTO){

        Event event = fetchEventById(ticketDTO.getEventId());

        Ticket ticket = Mapper.toTicket(ticketDTO, event);

        Ticket savedTicket = ticketRepository.save(ticket);

        emailService.sendEmail(ticket);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedTicket);
    }

    @Operation(summary = "Get ticket by ID", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ticket details",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TicketResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ticket not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class)
                    )
            )
    })
    @GetMapping("/get-ticket/{id}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable("id") String id){
        Ticket ticket = ticketRepository.findActiveTicketById(id);

        if (ticket == null){
            throw new NotFoundException("Ticket not found with id: " + id);
        }

        return ResponseEntity.status(HttpStatus.OK).body(Mapper.toTicketResponseDTO(ticket));
    }

    @Operation(summary = "Check tickets linked to an event", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tickets linked to the event retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TicketResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No tickets found for this event",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class)
                    )
            )
    })
    @GetMapping("/check-tickets-by-event/{eventId}")
    public ResponseEntity<List<TicketDTO>> checkTicketsByEvent(@PathVariable("eventId") String eventId){
        List<Ticket> ticketsEvents = ticketRepository.findTicketByEventId(eventId);

        List<Ticket> filteredTickets = ticketsEvents.stream().filter(ticket -> ticket.getDeleted().equals(false)).collect(Collectors.toList());

        List<TicketDTO> ticketDTOSList = Mapper.toListDTO(filteredTickets);

        if(ticketDTOSList.isEmpty()){
            throw new NotFoundException("No tickets found for this event");
        }

        return ResponseEntity.status(HttpStatus.OK).body(ticketDTOSList);
    }

    @Operation(summary = "Update a ticket by ID", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ticket updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Event not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class)
                    )
            )
    })
    @PutMapping("/update-ticket/{id}")
    public ResponseEntity<Ticket> updateTicketById(@PathVariable("id") String id, @RequestBody TicketDTO ticketDTO){

        Ticket ticket = ticketRepository.findActiveTicketById(id);

        if(ticket == null){
            throw new NotFoundException("Ticket not found with id: " + id);
        }

        Event event = fetchEventById(ticketDTO.getEventId());
        if (event == null){
            throw new NotFoundException("Event not found with id: " + ticketDTO.getEventId());
        }

        ticket.setCostumerName(ticketDTO.getCostumerName());
        ticket.setCpf(ticketDTO.getCpf());
        ticket.setCustomerMail(ticketDTO.getCustomerMail());
        ticket.setEvent(event);
        ticket.setUSDamount(ticketDTO.getUSDamount());
        ticket.setBRLamount(ticketDTO.getBRLamount());

        Ticket savedTicket = ticketRepository.save(ticket);

        emailService.sendEmailUpdate(ticket);

        return ResponseEntity.status(HttpStatus.OK).body(savedTicket);
    }

    @Operation(summary = "Delete a ticket by ID", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ticket deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Void.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ticket not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class)
                    )
            )
    })
    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<Ticket> deleteTicketById(@PathVariable("id") String id){

        Ticket ticket = ticketRepository.findActiveTicketById(id);

        if(ticket == null){
            throw new NotFoundException("Ticket not found with id: " + id);
        }

        ticket.setDeleted(true);
        ticketRepository.save(ticket);

        return ResponseEntity.status(HttpStatus.OK).body(ticket);
    }

    private Event fetchEventById(String eventId){

        String url = "http://3.21.39.39:8080/api/v1/get-event/" + eventId;
        RestTemplate restTemplate = new RestTemplate();

        try{
            return restTemplate.getForObject(url, Event.class);
        } catch (Exception e){
            throw new RuntimeException("Event not found id: " + eventId);
        }
    }
}
