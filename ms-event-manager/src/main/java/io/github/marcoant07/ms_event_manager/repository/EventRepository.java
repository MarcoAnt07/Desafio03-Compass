package io.github.marcoant07.ms_event_manager.repository;

import io.github.marcoant07.ms_event_manager.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
}
