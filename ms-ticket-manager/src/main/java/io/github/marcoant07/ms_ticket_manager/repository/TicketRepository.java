package io.github.marcoant07.ms_ticket_manager.repository;

import io.github.marcoant07.ms_ticket_manager.dto.TicketDTO;
import io.github.marcoant07.ms_ticket_manager.dto.TicketResponseDTO;
import io.github.marcoant07.ms_ticket_manager.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {

    Ticket findTicketById(String id);

    @Query("{ '_id':  ?0, 'deleted':  false }")
    Ticket findActiveTicketById(String id);

    List<Ticket> findTicketByEventId(String eventId);
}
