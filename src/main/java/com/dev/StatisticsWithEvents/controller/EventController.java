package com.dev.StatisticsWithEvents.controller;

import com.dev.StatisticsWithEvents.entities.Event;
import com.dev.StatisticsWithEvents.entities.EventType;
import com.dev.StatisticsWithEvents.exception.RecordNotFoundException;
import com.dev.StatisticsWithEvents.forms.EventForm;
import com.dev.StatisticsWithEvents.forms.EventTypeForm;
import com.dev.StatisticsWithEvents.service.EventService;
import com.dev.StatisticsWithEvents.service.TypeEventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class EventController {

    @Autowired
    EventService eventServiceImpl;

    @Autowired
    TypeEventServiceImpl typeEventServiceImpl;

    //http://localhost:8080/events?pageNo=0&pageSize=20&sortBy=event
    @GetMapping("/events")
    public String getAllEvents(@RequestParam(defaultValue = "0") Integer pageNo,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "id") String sortBy, Model model, HttpServletRequest httpServletRequest) {

        List<Event> list = eventServiceImpl.getAllEvents(pageNo, pageSize, sortBy);
        List<EventForm> eventFormList = new ArrayList<>();

        for (Event event : list) {
            System.out.println();
            EventForm form = new EventForm(event.getId(), event.getEvent(), event.getDate(), event.getEventId().getEventType());
            eventFormList.add(form);
        }

        model.addAttribute("events", eventFormList);

        //для пагинации
        Page<Event> pagedResult = eventServiceImpl.getAllEventsWithPage(pageNo, pageSize, sortBy);
        model.addAttribute("page", pagedResult);
        return "list-events";
    }

    @GetMapping("/events/{id}")
    public String getEventTypeById(@PathVariable("id") Long id, Model model)
            throws RecordNotFoundException {
        Event entity = eventServiceImpl.getEventById(id);
        model.addAttribute("eventType", new EventTypeForm());

        if (entity == null){
            model.addAttribute("events", null);
        }else {
            List<EventForm> eventFormList = new ArrayList<>();
            eventFormList.add(new EventForm(entity.getId(), entity.getEvent(), entity.getDate(), entity.getEventId().getEventType()));
            model.addAttribute("events", eventFormList);
            //для пагинации в шаблоне есть атрибут "page", но по ID он не нужен
            model.addAttribute("page", null);
        }

        return "list-events";
    }

    // http://localhost:8080/editEvent/
    @GetMapping("/editEvent/{id}")
    public String editEventTypeById(@PathVariable("id") Long id, Model model, HttpServletRequest request)
            throws RecordNotFoundException {

        Event eventEntity = eventServiceImpl.getEventById(id);


        model.addAttribute("event", new EventForm(eventEntity.getId(), eventEntity.getEvent(), eventEntity.getDate(), eventEntity.getEventId().getEventType()));
        List<EventType> eventTypesList = typeEventServiceImpl.getAllEventTypes();
        model.addAttribute("eventTypes", eventTypesList);
        String currentEventType = eventEntity.getEventId().getEventType();

        return "add-edit-Event";
    }

    @GetMapping("/addEvent")
    public String editEvent(Model model, HttpServletRequest request) throws RecordNotFoundException {
        request.getSession().getAttribute("eventTypes");
        model.addAttribute("event", new EventForm());

        List<EventType> eventTypesList = typeEventServiceImpl.getAllEventTypes();

        model.addAttribute("eventTypes", eventTypesList);

        return "add-edit-Event";
    }

    @PostMapping("addEvent")
    public String createEvent(@Valid @ModelAttribute("classificatorForm") EventForm eventForm, BindingResult bindingResult, Model model) throws RecordNotFoundException {

        if (bindingResult.hasErrors()) {
            return "add-edit-Event";
        }

        EventType eventTypeEntity = typeEventServiceImpl.findEventByName(eventForm.getEventType());

        Event eventEntity = new Event();
        LocalDate localDate = LocalDate.now();
        eventEntity.setId(eventForm.getId());
        eventEntity.setEvent(eventForm.getEvent());
        eventEntity.setDate(localDate);
        eventEntity.setEventId(eventTypeEntity);
        eventServiceImpl.createOrUpdateEvent(eventEntity);

        return "redirect:/events";
    }

    @GetMapping("/deleteEvent/{id}")
    public String deleteEventById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        eventServiceImpl.deleteEventById(id);
        return "redirect:/events";
    }



}
