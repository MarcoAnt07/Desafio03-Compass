package io.github.marcoant07.ms_ticket_manager.repository;

import io.github.marcoant07.ms_ticket_manager.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {

    Ticket findTicketById(String id);
}
