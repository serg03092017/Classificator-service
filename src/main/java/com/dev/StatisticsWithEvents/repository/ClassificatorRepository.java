package com.dev.StatisticsWithEvents.repository;


import com.dev.StatisticsWithEvents.entities.Event;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface ClassificatorRepository extends PagingAndSortingRepository<Event, Long> {
}
