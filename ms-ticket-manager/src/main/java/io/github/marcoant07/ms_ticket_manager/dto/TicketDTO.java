package io.github.marcoant07.ms_ticket_manager.dto;

import io.github.marcoant07.ms_ticket_manager.entity.Event;

public class TicketDTO {

    private Long id;
    private String costumerName;
    private String cpf;
    private String custumerMail;
    private String eventId;
    private Double BRLamount;
    private Double USDamount;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
