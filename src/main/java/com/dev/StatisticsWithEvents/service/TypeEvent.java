package com.dev.StatisticsWithEvents.service;

import com.dev.StatisticsWithEvents.entities.EventType;
import com.dev.StatisticsWithEvents.exception.RecordNotFoundException;
import org.springframework.data.domain.Page;


import java.util.List;


public interface TypeEvent {

    List<EventType> getAllEventTypes(Integer pageNo, Integer pageSize, String sortBy);

    EventType getEventTypeById(Long id) throws RecordNotFoundException;

    EventType createOrUpdateEventType(EventType entity) throws RecordNotFoundException;

    void deleteEventTypeById(Long id) throws RecordNotFoundException;

    List<EventType> getAllEventTypes() throws RecordNotFoundException;

    EventType findEventByName(String eventTypeName);

    Page<EventType> getAllEventsTypesWithPage(Integer pageNo, Integer pageSize, String sortBy);
}
