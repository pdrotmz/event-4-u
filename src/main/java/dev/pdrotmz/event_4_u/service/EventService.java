package dev.pdrotmz.event_4_u.service;

import dev.pdrotmz.event_4_u.domain.model.Event;
import dev.pdrotmz.event_4_u.domain.model.User;
import dev.pdrotmz.event_4_u.repository.EventRepository;
import dev.pdrotmz.event_4_u.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private UserRepository userRepository;

    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public Event createEvent(Event event, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not Found"));
        event.setUser(user);
        return repository.save(event);
    }

    public List<Event> getAllEvents() {
        return repository.findAll();
    }

    public Optional<Event> getEventByID(UUID id) {
        return repository.findById(id);
    }

    public Event updateEvent(UUID id, Event updateEventInfo) {
        return repository.findById(id).map(Event -> {
            Event.setEventTitle(updateEventInfo.getEventTitle());
            Event.setEventDescription(updateEventInfo.getEventDescription());
            return repository.save(Event);
        }).orElseThrow(() -> new EntityNotFoundException("Event not found"));
    }

    public void deleteEventById(UUID id) {
        repository.deleteById(id);
    }
}
