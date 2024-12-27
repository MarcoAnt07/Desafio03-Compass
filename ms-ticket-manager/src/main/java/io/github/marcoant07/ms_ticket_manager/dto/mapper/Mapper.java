package io.github.marcoant07.ms_ticket_manager.dto.mapper;

import io.github.marcoant07.ms_ticket_manager.dto.EmailDTO;
import io.github.marcoant07.ms_ticket_manager.dto.TicketDTO;
import io.github.marcoant07.ms_ticket_manager.entity.Email;
import io.github.marcoant07.ms_ticket_manager.entity.Event;
import io.github.marcoant07.ms_ticket_manager.entity.Ticket;
import org.modelmapper.ModelMapper;

public class Mapper {

    public static Email toEmail(EmailDTO emailDTO){

        Email email = new Email();
        email.setEmailTo(emailDTO.getEmailTo());
        email.setSubject(emailDTO.getSubject());
        email.setText(emailDTO.getText());

        return email;
    }

    public static EmailDTO toEmailDTO(Email email){
        return new ModelMapper().map(email, EmailDTO.class);
    }

    public static Ticket toTicket(TicketDTO ticketDTO, Event event){

        Ticket ticket = new Ticket();
        ticket.setCostumerName(ticketDTO.getCostumerName());
        ticket.setCpf(ticketDTO.getCpf());
        ticket.setCustumerMail(ticketDTO.getCustumerMail());
        ticket.setEvent(event);
        ticket.setBRLamount(ticketDTO.getBRLamount());
        ticket.setUSDamount(ticketDTO.getUSDamount());

        return ticket;
    }

    public static TicketDTO toTicketDTO(Ticket ticket){
        return new ModelMapper().map(ticket, TicketDTO.class);
    }
}
