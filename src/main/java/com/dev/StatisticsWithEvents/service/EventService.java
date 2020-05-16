package com.dev.StatisticsWithEvents.service;

import com.dev.StatisticsWithEvents.entities.Event;
import com.dev.StatisticsWithEvents.exception.RecordNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;


public interface EventService {

    List<Event> getAllEvents(Integer pageNo, Integer pageSize, String sortBy);

    Page<Event> getAllEventsWithPage(Integer pageNo, Integer pageSize, String sortBy);

    Event getEventById(Long id) throws RecordNotFoundException;

    Event createOrUpdateEvent(Event entity) throws RecordNotFoundException;

    void deleteEventById(Long id) throws RecordNotFoundException;

}
