package io.github.marcoant07.ms_ticket_manager.services;

import io.github.marcoant07.ms_ticket_manager.entity.Ticket;
import io.github.marcoant07.ms_ticket_manager.publisher.TicketPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TicketPublisher ticketPublisher;

    @Transactional
    public void sendEmail(Ticket ticket){

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(ticket.getCustomerMail());
            message.setSubject("Ticket Confirmation");
            message.setText(
                            "Ticket Data: " + "\n\n" +
                            "Ticket ID: " + ticket.getId() + "\n" +
                            "Customer Name: " + ticket.getCostumerName() + "\n" +
                            "Event ID: " + ticket.getEvent().getId() + "\n" +
                            "Event Name: " + ticket.getEvent().getEventName() + "\n" +
                            "Event Date and Time: " + ticket.getEvent().getDateTime() + "\n" +
                            "Event CEP: " + ticket.getEvent().getCep() + "\n" +
                            "Event Public Place: " + ticket.getEvent().getLogradouro() + "\n" +
                            "Event Neighborhood: " + ticket.getEvent().getBairro() + "\n" +
                            "Event City: " + ticket.getEvent().getCidade() + "\n" +
                            "Event UF: " + ticket.getEvent().getUf() + "\n" +
                            "BRL Amount: " + ticket.getBRLamount() + "\n" +
                            "USD Amount: " + ticket.getUSDamount()
            );
            emailSender.send(message);

            ticketPublisher.solicitarTicket(ticket);

    }

    @Transactional
    public void sendEmailUpdate(Ticket ticket){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(ticket.getCustomerMail());
        message.setSubject("Ticket Update");
        message.setText(
                        "Updated Ticket Data: " + "\n\n" +
                        "Ticket ID: " + ticket.getId() + "\n" +
                        "Customer Name: " + ticket.getCostumerName() + "\n" +
                        "Event ID: " + ticket.getEvent().getId() + "\n" +
                        "Event Name: " + ticket.getEvent().getEventName() + "\n" +
                        "Event Date and Time: " + ticket.getEvent().getDateTime() + "\n" +
                        "Event CEP: " + ticket.getEvent().getCep() + "\n" +
                        "Event Public Place: " + ticket.getEvent().getLogradouro() + "\n" +
                        "Event Neighborhood: " + ticket.getEvent().getBairro() + "\n" +
                        "Event City: " + ticket.getEvent().getCidade() + "\n" +
                        "Event UF: " + ticket.getEvent().getCidade() + "\n" +
                        "BRL Amount: " + ticket.getBRLamount() + "\n" +
                        "USD Amount: " + ticket.getUSDamount()
        );
        emailSender.send(message);

        ticketPublisher.solicitarTicket(ticket);

    }
}
