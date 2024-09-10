package dev.pdrotmz.event_4_u.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Entity
@Table(name = "tb_event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String eventTitle;

    @NotBlank
    private String eventDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_name", nullable = false)
    @JsonBackReference
    private User user;

    public Event() {
    }

    public Event(UUID id, String eventTitle, String eventDescription, User user) {
        this.id = id;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
