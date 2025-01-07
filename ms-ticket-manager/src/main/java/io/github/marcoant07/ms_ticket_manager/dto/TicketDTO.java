package io.github.marcoant07.ms_ticket_manager.dto;

import io.github.marcoant07.ms_ticket_manager.entity.Event;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class TicketDTO {

    private String id;

    @NotBlank
    @Size(max = 255)
    private String costumerName;

    @NotBlank
    @Size(min = 11, max = 14)
    private String cpf;

    @Email
    private String customerMail;

    @NotBlank
    private String eventId;

    @NotBlank
    private Double BRLamount;

    @NotBlank
    private Double USDamount;
    private Boolean deleted;

    public TicketDTO() {
    }

    public TicketDTO(String costumerName, String cpf, String customerMail, Event event, Double BRLamount, Double USDamount) {
        this.costumerName = costumerName;
        this.cpf = cpf;
        this.customerMail = customerMail;
        this.eventId = event.getId();
        this.BRLamount = BRLamount;
        this.USDamount = USDamount;
    }

    public TicketDTO(String id, String costumerName, String cpf, String customerMail, String eventId, Double BRLamount, Double USDamount, Boolean deleted) {
        this.id = id;
        this.costumerName = costumerName;
        this.cpf = cpf;
        this.customerMail = customerMail;
        this.eventId = eventId;
        this.BRLamount = BRLamount;
        this.USDamount = USDamount;
        this.deleted = deleted;
    }

    public TicketDTO(String id, String costumerName, String cpf, String eventId, String customerMail, Double BRLamount, Double USDamount) {
        this.id = id;
        this.costumerName = costumerName;
        this.cpf = cpf;
        this.eventId = eventId;
        this.customerMail = customerMail;
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

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
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
        return Objects.equals(id, ticketDTO.id) && Objects.equals(costumerName, ticketDTO.costumerName) && Objects.equals(cpf, ticketDTO.cpf) && Objects.equals(customerMail, ticketDTO.customerMail) && Objects.equals(eventId, ticketDTO.eventId) && Objects.equals(BRLamount, ticketDTO.BRLamount) && Objects.equals(USDamount, ticketDTO.USDamount) && Objects.equals(deleted, ticketDTO.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, costumerName, cpf, customerMail, eventId, BRLamount, USDamount, deleted);
    }
}
