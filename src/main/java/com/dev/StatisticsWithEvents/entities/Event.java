package com.dev.StatisticsWithEvents.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="classificator")
public class Event implements Serializable {

    private Long id;

    private String event;

    private java.time.LocalDate date;

    private EventType eventId;

    public Event() {
    }

    public Event(Long id, String event, LocalDate date, EventType eventId) {
        this.id = id;
        this.event = event;
        this.date = date;
        this.eventId = eventId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "Event")
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Column(name = "Date_Add")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "Event_Type_Id")

    public EventType getEventId() {
        return eventId;
    }

    public void setEventId(EventType eventId) {
        this.eventId = eventId;
    }
}




