package com.dev.StatisticsWithEvents.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "events_types")
public class EventType implements Serializable {


    private Long id;

    private String eventType;

    private List<Event> event;

    public EventType() {
    }

    public EventType(Long id, String eventType, List<Event> event) {
        this.id = id;
        this.eventType = eventType;
        this.event = event;
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
    @Column(name = "Event_Type")
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }



    @OneToMany(mappedBy = "eventId", fetch = FetchType.EAGER)
    public List<Event> getEvent() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }

    public void addValute(Event event) {
        event.setEventId(this);
        getEvent().add(event);
    }

    public void removeContactTelDetail(Event event) {
        getEvent().remove(event);
    }

}
