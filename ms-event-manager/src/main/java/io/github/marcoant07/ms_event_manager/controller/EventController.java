package io.github.marcoant07.ms_event_manager.controller;

import io.github.marcoant07.ms_event_manager.dto.Address;
import io.github.marcoant07.ms_event_manager.dto.EventDTO;
import io.github.marcoant07.ms_event_manager.dto.GetEventDTO;
import io.github.marcoant07.ms_event_manager.dto.mapper.Mapper;
import io.github.marcoant07.ms_event_manager.entity.Event;
import io.github.marcoant07.ms_event_manager.entity.Ticket;
import io.github.marcoant07.ms_event_manager.exception.throwable.ConflictException;
import io.github.marcoant07.ms_event_manager.exception.throwable.NotFoundException;
import io.github.marcoant07.ms_event_manager.repository.EventRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Operation(summary = "Create a new event", responses = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Resource created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Event.class)
                    )
            )
    })
    @PostMapping("/create-event")
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO eventDTO){

        String url = "https://viacep.com.br/ws/" + eventDTO.getCep() + "/json/";
        RestTemplate restTemplate = new RestTemplate();
        Address address = restTemplate.getForObject(url, Address.class);

        Event event = Mapper.toEvent(eventDTO);
        event.setLogradouro(address.getLogradouro());
        event.setBairro(address.getBairro());
        event.setCidade(address.getLocalidade());
        event.setUf(address.getUf());

        Event savedEvent = eventRepository.save(event);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    @Operation(summary = "Get all events", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all events",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetEventDTO.class)
                    )
            )
    })
    @GetMapping("/get-all-events")
    public ResponseEntity<List<GetEventDTO>> getAllEvents(){
        List<Event> events = eventRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(Mapper.toListDTO(events));
    }

    @Operation(summary = "Get all events sorted", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of all events sorted",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetEventDTO.class)
                    )
            )
    })
    @GetMapping("/get-all-events/sorted")
    public ResponseEntity<List<GetEventDTO>> getAllEventsSorted(){
        List<Event> events = eventRepository.findAll(Sort.by(Sort.Direction.ASC, "eventName"));

        return ResponseEntity.status(HttpStatus.OK).body(Mapper.toListDTO(events));
    }

    @Operation(summary = "Get event by ID", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Event details",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetEventDTO.class)
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
    @GetMapping("/get-event/{id}")
    public ResponseEntity<GetEventDTO> getById(@PathVariable("id") String id){

        Event event = eventRepository.findById(id).orElseThrow(
                NotFoundException::new
        );

        return ResponseEntity.status(HttpStatus.OK).body(Mapper.toEventDTO(event));
    }

    @Operation(summary = "Update an event by ID", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Event updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Event.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Event not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict - Tickets linked to this event",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ConflictException.class)
                    )
            )
    })
    @PutMapping("/update-event/{id}")
    public ResponseEntity<Event> updateEventById(@PathVariable("id") String id, @RequestBody EventDTO eventDTO){

        String urlGet = "http://3.131.35.32:8080/api/v1/check-tickets-by-event/" + id;
        RestTemplate restTemplate = new RestTemplate();

        List<Ticket> ticketList;

        try{
            ticketList = restTemplate.exchange(urlGet, HttpMethod.GET, null, new ParameterizedTypeReference<List<Ticket>>() {}).getBody();
        } catch (HttpClientErrorException.NotFound e){
            ticketList = Collections.emptyList();
        }

        if (ticketList.isEmpty()){
            Event event = eventRepository.findById(id).orElseThrow(
                    NotFoundException::new
            );

            String urlViaCep = "https://viacep.com.br/ws/" + eventDTO.getCep() + "/json/";
            Address address = restTemplate.getForObject(urlViaCep, Address.class);

            event.setEventName(eventDTO.getNameEvent());
            event.setDateTime(eventDTO.getDateTime());
            event.setCep(eventDTO.getCep());
            event.setLogradouro(address.getLogradouro());
            event.setBairro(address.getBairro());
            event.setCidade(address.getLocalidade());
            event.setUf(address.getUf());

            Event savedEvent = eventRepository.save(event);

            return ResponseEntity.ok(savedEvent);
        } else {
            throw new ConflictException("There are tickets registered for this event");
        }
    }

    @Operation(summary = "Delete an event by ID", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Event deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Void.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Event not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotFoundException.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict - Tickets linked to this event",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ConflictException.class)
                    )
            )
    })
    @DeleteMapping("/delete-event/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable String id){

        String url = "http://3.131.35.32:8080/api/v1/check-tickets-by-event/" + id;
        RestTemplate restTemplate = new RestTemplate();

        List<Ticket> ticketList;

        try{
            ticketList = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Ticket>>() {}).getBody();
        } catch (HttpClientErrorException.NotFound e){
            ticketList = Collections.emptyList();
        }


        Event event = eventRepository.findById(id).orElseThrow(
                NotFoundException::new
        );

        if (ticketList.isEmpty()){
            eventRepository.deleteById(id);

            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            throw new ConflictException("Conflict - Tickets linked to this event");
        }
    }
}
