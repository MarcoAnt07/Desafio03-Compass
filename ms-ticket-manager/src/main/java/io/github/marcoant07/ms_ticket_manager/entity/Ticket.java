package io.github.marcoant07.ms_ticket_manager.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "db_ticket")
@Data
public class Ticket {

    @Id
    private String id;
    private String costumerName;
    private String cpf;
    private String customerMail;
    private Event event;
    private Double BRLamount;
    private Double USDamount;
    private Boolean deleted = false;

    public Ticket() {
    }

    public Ticket(String costumerName, String cpf, String customerMail, Event event, Double BRLamount, Double USDamount) {
        this.costumerName = costumerName;
        this.cpf = cpf;
        this.customerMail = customerMail;
        this.event = event;
        this.BRLamount = BRLamount;
        this.USDamount = USDamount;
    }

    public Ticket(String id, String costumerName, String cpf, String customerMail, Event event, Double BRLamount, Double USDamount, Boolean deleted) {
        this.id = id;
        this.costumerName = costumerName;
        this.cpf = cpf;
        this.customerMail = customerMail;
        this.event = event;
        this.BRLamount = BRLamount;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
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
        return Objects.equals(id, ticket.id) && Objects.equals(costumerName, ticket.costumerName) && Objects.equals(cpf, ticket.cpf) && Objects.equals(customerMail, ticket.customerMail) && Objects.equals(event, ticket.event) && Objects.equals(BRLamount, ticket.BRLamount) && Objects.equals(USDamount, ticket.USDamount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, costumerName, cpf, customerMail, event, BRLamount, USDamount);
    }
}
