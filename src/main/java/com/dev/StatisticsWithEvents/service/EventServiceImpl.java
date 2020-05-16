package com.dev.StatisticsWithEvents.service;

import com.dev.StatisticsWithEvents.entities.Event;
import com.dev.StatisticsWithEvents.entities.EventType;
import com.dev.StatisticsWithEvents.exception.RecordNotFoundException;
import com.dev.StatisticsWithEvents.repository.ClassificatorRepository;
import com.dev.StatisticsWithEvents.repository.TypeEventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService{
    @Autowired
    ClassificatorRepository repository;

    @Autowired
    TypeEventsRepository typeEventsRepository;

    public List<Event> getAllEvents(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Event> pagedResult = repository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Event>();
        }
    }

    public Page<Event> getAllEventsWithPage(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Event> pagedResult = repository.findAll(paging);
        return pagedResult;

    }

    public Event getEventById(Long id) throws RecordNotFoundException {
        Optional<Event> event = repository.findById(id);

        if (event.isPresent()) {
            return event.get();
        } else {
            return null;
        }
    }

    public Event createOrUpdateEvent(Event entity) throws RecordNotFoundException {

        if (entity.getId() != null) {
            Optional<Event> event = repository.findById(entity.getId());
            EventType eventType = typeEventsRepository.findByEventType(entity.getEventId().getEventType());


            if (event.isPresent()) {
                Event newEvent = event.get();
                newEvent.setEvent(entity.getEvent());
                newEvent.setEventId(eventType);

                newEvent = repository.save(newEvent);

                return newEvent;
            } else {
                entity = repository.save(entity);

                return entity;
            }
        } else {
            entity = repository.save(entity);
            return entity;
        }

    }

    public void deleteEventById(Long id) throws RecordNotFoundException {
        Optional<Event> event = repository.findById(id);

        if (event.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }

    public Event save(Event event) {
        return repository.save(event);
    }

}