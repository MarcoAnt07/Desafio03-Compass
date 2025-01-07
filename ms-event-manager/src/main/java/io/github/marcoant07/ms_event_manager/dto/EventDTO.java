package io.github.marcoant07.ms_event_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class EventDTO {

    @NotBlank
    @Size(max = 255)
    private String nameEvent;

    @NotBlank
    private LocalDateTime dateTime;

    @NotBlank
    @Size(max = 9, min = 9)
    private String cep;

    public EventDTO() {
    }

    public EventDTO(String nameEvent, LocalDateTime dateTime, String cep) {
        this.nameEvent = nameEvent;
        this.dateTime = dateTime;
        this.cep = cep;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}