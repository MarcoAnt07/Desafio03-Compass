package io.github.marcoant07.ms_ticket_manager.dto;

import io.github.marcoant07.ms_ticket_manager.entity.Event;

import java.util.Objects;

public class TicketDTO {

    private String id;
    private String costumerName;
    private String cpf;
    private String custumerMail;
    private String eventId;
    private Double BRLamount;
    private Double USDamount;
    private Boolean deleted;

    public TicketDTO() {
    }

    public TicketDTO(String costumerName, String cpf, String custumerMail, Event event, Double BRLamount, Double USDamount) {
        this.costumerName = costumerName;
        this.cpf = cpf;
        this.custumerMail = custumerMail;
        this.eventId = event.getId();
        this.BRLamount = BRLamount;
        this.USDamount = USDamount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCostumerName() {
        return costumerName;
    }

    public void setCostumerName(String costumerName) {
        this.costumerName = costumerName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCustumerMail() {
        return custumerMail;
    }

    public void setCustumerMail(String custumerMail) {
        this.custumerMail = custumerMail;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Double getBRLamount() {
        return BRLamount;
    }

    public void setBRLamount(Double BRLamount) {
        this.BRLamount = BRLamount;
    }

    public Double getUSDamount() {
        return USDamount;
    }

    public void setUSDamount(Double USDamount) {
        this.USDamount = USDamount;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketDTO ticketDTO = (TicketDTO) o;
        return Objects.equals(id, ticketDTO.id) && Objects.equals(costumerName, ticketDTO.costumerName) && Objects.equals(cpf, ticketDTO.cpf) && Objects.equals(custumerMail, ticketDTO.custumerMail) && Objects.equals(eventId, ticketDTO.eventId) && Objects.equals(BRLamount, ticketDTO.BRLamount) && Objects.equals(USDamount, ticketDTO.USDamount) && Objects.equals(deleted, ticketDTO.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, costumerName, cpf, custumerMail, eventId, BRLamount, USDamount, deleted);
    }
}
