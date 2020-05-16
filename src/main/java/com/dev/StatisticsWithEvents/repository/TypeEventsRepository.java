package com.dev.StatisticsWithEvents.repository;

import com.dev.StatisticsWithEvents.entities.EventType;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TypeEventsRepository extends PagingAndSortingRepository<EventType, Long> {

    EventType findByEventType(String eventType);
}
