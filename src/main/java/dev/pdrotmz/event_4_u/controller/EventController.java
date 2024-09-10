package dev.pdrotmz.event_4_u.controller;

import dev.pdrotmz.event_4_u.domain.Event;
import dev.pdrotmz.event_4_u.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }


    @PostMapping("register-event")
    public ResponseEntity registerEvent(@RequestBody Event event, @RequestParam String username) {
        service.createEvent(event, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = service.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventByID(@PathVariable UUID id) {
        Optional<Event> event = service.getEventByID(id);
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable UUID id, @RequestBody Event event) {
        Event updatedEvent = service.updateEvent(id, event);
        return ResponseEntity.ok().body(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID id) {
        service.deleteEventById(id);
        return ResponseEntity.noContent().build();
    }
}
