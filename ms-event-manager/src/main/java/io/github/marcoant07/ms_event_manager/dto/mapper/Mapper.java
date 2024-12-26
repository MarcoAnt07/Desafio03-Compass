package io.github.marcoant07.ms_event_manager.dto.mapper;

import io.github.marcoant07.ms_event_manager.dto.EventDTO;
import io.github.marcoant07.ms_event_manager.dto.GetEventDTO;
import io.github.marcoant07.ms_event_manager.entity.Event;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static Event toEvent(EventDTO eventDTO){

        Event event = new Event();
        event.setEventName(eventDTO.getNameEvent());
        event.setCep(eventDTO.getCep());
        event.setDateTime(eventDTO.getDateTime());


        return event;
    }

    public static GetEventDTO toEventDTO(Event event){
        return new ModelMapper().map(event, GetEventDTO.class);
    }

    public static List<GetEventDTO> toListDTO(List<Event> events){
        return events.stream().map(Mapper::toEventDTO).collect(Collectors.toList());
    }
}
