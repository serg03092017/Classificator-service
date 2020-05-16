package com.dev.StatisticsWithEvents.service;


import com.dev.StatisticsWithEvents.entities.EventType;
import com.dev.StatisticsWithEvents.exception.RecordNotFoundException;
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
public class TypeEventServiceImpl implements TypeEvent {

    @Autowired
    TypeEventsRepository repository;

    public List<EventType> getAllEventTypes(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<EventType> pagedResult = repository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<EventType>();
        }
    }

    public EventType getEventTypeById(Long id) throws RecordNotFoundException {
        Optional<EventType> eventType = repository.findById(id);

        if (eventType.isPresent()) {
            return eventType.get();
        } else {
            return null;
        }
    }

    public EventType createOrUpdateEventType(EventType entity) throws RecordNotFoundException {

        if (entity.getId() != null) {
            Optional<EventType> eventType = repository.findById(entity.getId());

            if (eventType.isPresent()) {
                EventType newEvent = eventType.get();
                newEvent.setEventType(entity.getEventType());

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

    public void deleteEventTypeById(Long id) throws RecordNotFoundException {
        Optional<EventType> eventType = repository.findById(id);

        if (eventType.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No eventType record exist for given id");
        }
    }

    public EventType save(EventType eventType) {
        return repository.save(eventType);
    }

    public List<EventType> getAllEventTypes() throws RecordNotFoundException {
        return (List<EventType>) repository.findAll();
    }

    public EventType findEventByName(String eventTypeName) {
        return repository.findByEventType(eventTypeName);
    }

    public Page<EventType> getAllEventsTypesWithPage(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<EventType> pagedResult = repository.findAll(paging);
        return pagedResult;
    }
}