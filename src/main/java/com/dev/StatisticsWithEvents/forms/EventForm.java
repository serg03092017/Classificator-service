package com.dev.StatisticsWithEvents.forms;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventForm {


    private Long id;

    private String event;

    private java.time.LocalDate date;

    private String eventType;

    private String eventTypeName;

    public EventForm() {
    }

    public EventForm(Long id, String event, LocalDate date, String eventTypeName) {
        this.id = id;
        this.event = event;
        this.date = date;
        this.eventTypeName = eventTypeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }
}
