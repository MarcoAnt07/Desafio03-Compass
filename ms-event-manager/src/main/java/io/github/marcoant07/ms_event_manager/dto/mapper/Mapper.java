package io.github.marcoant07.ms_event_manager.dto.mapper;

import io.github.marcoant07.ms_event_manager.dto.CreateEventDTO;
import io.github.marcoant07.ms_event_manager.entity.Event;
import io.github.marcoant07.ms_event_manager.repository.EventRepository;
import org.springframework.web.client.RestTemplate;

public class Mapper {

    public static Event toEvent(CreateEventDTO eventDTO){

        Event event = new Event();
        event.setEventName(eventDTO.getNameEvent());
        event.setCep(eventDTO.getCep());
        event.setDateTime(eventDTO.getDateTime());


        return event;
    }
}
