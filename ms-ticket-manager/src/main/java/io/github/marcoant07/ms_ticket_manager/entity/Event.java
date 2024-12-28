package io.github.marcoant07.ms_ticket_manager.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class Event {

    private String id;
    private String eventName;
    private LocalDateTime dateTime;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;

    public Event() {
    }

    public Event(LocalDateTime dateTime, String eventName, String cep, String logradouro, String bairro, String cidade, String uf) {
        this.dateTime = dateTime;
        this.eventName = eventName;
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    @JsonCreator
    public Event(@JsonProperty("id") String id,
                 @JsonProperty("nameEvent") String eventName,
                 @JsonProperty("dateTime") LocalDateTime dateTime,
                 @JsonProperty("cep") String cep,
                 @JsonProperty("logradouro") String logradouro,
                 @JsonProperty("bairro") String bairro,
                 @JsonProperty("cidade") String cidade,
                 @JsonProperty("uf") String uf) {
        this.id = id;
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(eventName, event.eventName) && Objects.equals(dateTime, event.dateTime) && Objects.equals(cep, event.cep) && Objects.equals(logradouro, event.logradouro) && Objects.equals(bairro, event.bairro) && Objects.equals(cidade, event.cidade) && Objects.equals(uf, event.uf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventName, dateTime, cep, logradouro, bairro, cidade, uf);
    }
}
