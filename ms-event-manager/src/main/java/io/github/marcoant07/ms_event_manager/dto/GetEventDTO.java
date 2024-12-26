package io.github.marcoant07.ms_event_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
public class GetEventDTO {

    private String id;
    private String nameEvent;
    private LocalDateTime dateTime;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;

    public GetEventDTO() {
    }

    public GetEventDTO(String id, String nameEvent, String cep, LocalDateTime dateTime, String logradouro, String bairro, String cidade, String uf) {
        this.id = id;
        this.nameEvent = nameEvent;
        this.cep = cep;
        this.dateTime = dateTime;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
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
        GetEventDTO that = (GetEventDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(nameEvent, that.nameEvent) && Objects.equals(dateTime, that.dateTime) && Objects.equals(cep, that.cep) && Objects.equals(logradouro, that.logradouro) && Objects.equals(bairro, that.bairro) && Objects.equals(cidade, that.cidade) && Objects.equals(uf, that.uf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameEvent, dateTime, cep, logradouro, bairro, cidade, uf);
    }


}
