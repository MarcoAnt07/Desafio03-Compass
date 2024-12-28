package io.github.marcoant07.ms_ticket_manager.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.marcoant07.ms_ticket_manager.entity.Email;
import io.github.marcoant07.ms_ticket_manager.entity.Ticket;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TicketPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.queue}")
    private String queueEmissaoTicket;

    public void solicitarTicket(Ticket ticket) {
        rabbitTemplate.convertAndSend(queueEmissaoTicket, ticket);
    }

    private String convertIntoJson(Email emailDTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(emailDTO);

        return json;
    }
}
