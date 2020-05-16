package com.dev.StatisticsWithEvents.forms;


import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
public class EventTypeForm {

    private Long id;
    @NotNull
    @Size(min = 1, max = 30)
    private String eventType;

    public EventTypeForm() {
    }

    public EventTypeForm(Long id, String eventType) {
        this.id = id;
        this.eventType = eventType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
