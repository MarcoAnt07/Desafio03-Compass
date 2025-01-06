package io.github.marcoant07.ms_ticket_manager.dto;

import io.github.marcoant07.ms_ticket_manager.entity.Event;

import java.util.Objects;

public class TicketResponseDTO {

    private String id;
    private String costumerName;
    private String cpf;
    private String customerMail;
    private Event event;
    private Double BRLamount;
    private Double USDamount;
    private Boolean deleted;

    public TicketResponseDTO(String costumerName, String cpf, String custumerMail, Event event, Double BRLamount, Double USDamount) {
        this.costumerName = costumerName;
        this.cpf = cpf;
        this.customerMail = custumerMail;
        this.event = event;
        this.BRLamount = BRLamount;
        this.USDamount = USDamount;
    }

    public TicketResponseDTO() {
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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
        TicketResponseDTO that = (TicketResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(costumerName, that.costumerName) && Objects.equals(cpf, that.cpf) && Objects.equals(customerMail, that.customerMail) && Objects.equals(event, that.event) && Objects.equals(BRLamount, that.BRLamount) && Objects.equals(USDamount, that.USDamount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, costumerName, cpf, customerMail, event, BRLamount, USDamount);
    }
}
