package io.github.marcoant07.ms_event_manager.entity;

import java.util.Objects;

public class Ticket {

    private String id;
    private String costumerName;
    private String cpf;
    private String custumerMail;
    private String eventId;
    private Double BRLamount;
    private Double USDamount;
    private Boolean deleted;

    public Ticket() {
    }

    public Ticket(String id, String costumerName, String cpf, String custumerMail, String eventId, Double BRLamount, Double USDamount, Boolean deleted) {
        this.id = id;
        this.costumerName = costumerName;
        this.cpf = cpf;
        this.custumerMail = custumerMail;
        this.eventId = eventId;
        this.BRLamount = BRLamount;
        this.USDamount = USDamount;
        this.deleted = deleted;
    }

    public Ticket(String costumerName, String cpf, String custumerMail, Double BRLamount, String eventId, Double USDamount, Boolean deleted) {
        this.costumerName = costumerName;
        this.cpf = cpf;
        this.custumerMail = custumerMail;
        this.BRLamount = BRLamount;
        this.eventId = eventId;
        this.USDamount = USDamount;
        this.deleted = deleted;
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
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && Objects.equals(costumerName, ticket.costumerName) && Objects.equals(cpf, ticket.cpf) && Objects.equals(custumerMail, ticket.custumerMail) && Objects.equals(eventId, ticket.eventId) && Objects.equals(BRLamount, ticket.BRLamount) && Objects.equals(USDamount, ticket.USDamount) && Objects.equals(deleted, ticket.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, costumerName, cpf, custumerMail, eventId, BRLamount, USDamount, deleted);
    }
}
