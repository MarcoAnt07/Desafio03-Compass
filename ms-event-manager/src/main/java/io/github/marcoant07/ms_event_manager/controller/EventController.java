package io.github.marcoant07.ms_event_manager.controller;

import io.github.marcoant07.ms_event_manager.dto.Address;
import io.github.marcoant07.ms_event_manager.dto.CreateEventDTO;
import io.github.marcoant07.ms_event_manager.dto.GetEventDTO;
import io.github.marcoant07.ms_event_manager.dto.mapper.Mapper;
import io.github.marcoant07.ms_event_manager.entity.Event;
import io.github.marcoant07.ms_event_manager.errors.NotFoundException;
import io.github.marcoant07.ms_event_manager.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("event")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @PostMapping("/create-event")
    public ResponseEntity<Event> createEvent(@RequestBody CreateEventDTO createEventDTO){

        String url = "https://viacep.com.br/ws/" + createEventDTO.getCep() + "/json/";
        RestTemplate restTemplate = new RestTemplate();
        Address address = restTemplate.getForObject(url, Address.class);

        Event event = Mapper.toEvent(createEventDTO);
        event.setLogradouro(address.getLogradouro());
        event.setBairro(address.getBairro());
        event.setCidade(address.getLocalidade());
        event.setUf(address.getUf());

        Event savedEvent = eventRepository.save(event);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    @GetMapping("/get-all-events")
    public ResponseEntity<List<GetEventDTO>> getAllEvents(){
        List<Event> events = eventRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(Mapper.toListDTO(events));
    }

    @GetMapping("/get-event/{id}")
    public ResponseEntity<GetEventDTO> getById(@PathVariable("id") String id){

        Event event = eventRepository.findById(id).orElseThrow(NotFoundException::new);

        return ResponseEntity.status(HttpStatus.OK).body(Mapper.toEventDTO(event));
    }

    @DeleteMapping("/delete-event/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable String id){

        Event event = eventRepository.findById(id).orElseThrow(
                NotFoundException::new
        );

        eventRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
