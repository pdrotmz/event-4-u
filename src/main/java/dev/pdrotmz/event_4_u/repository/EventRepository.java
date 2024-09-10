package dev.pdrotmz.event_4_u.repository;

import dev.pdrotmz.event_4_u.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

}
