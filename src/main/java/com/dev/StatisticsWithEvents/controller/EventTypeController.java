package com.dev.StatisticsWithEvents.controller;

import com.dev.StatisticsWithEvents.entities.EventType;
import com.dev.StatisticsWithEvents.exception.RecordNotFoundException;
import com.dev.StatisticsWithEvents.forms.EventTypeForm;
import com.dev.StatisticsWithEvents.service.EventServiceImpl;
import com.dev.StatisticsWithEvents.service.TypeEventServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping
public class EventTypeController {

    @Autowired
    EventServiceImpl eventServiceImpl;

    @Autowired
    TypeEventServiceImpl typeEventServiceImpl;

    //http://localhost:8080/eventTypes?pageNo=0&pageSize=20&sortBy=eventType
    @GetMapping("/eventTypes")
    public String getAllEventTypes(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy, Model model) {
        List<EventType> list = typeEventServiceImpl.getAllEventTypes(pageNo, pageSize, sortBy);
        model.addAttribute("eventTypes", list);
        //для пагинации
        Page<EventType> pagedResult = typeEventServiceImpl.getAllEventsTypesWithPage(pageNo, pageSize, sortBy);
        model.addAttribute("page", pagedResult);
        return "list-event-types";
    }

    @GetMapping("/eventTypes/{id}")
    public String getEventTypeById(@PathVariable("id") Long id, Model model)
            throws RecordNotFoundException {
        EventType entity = typeEventServiceImpl.getEventTypeById(id);
        model.addAttribute("eventTypes", entity);
        //для пагинации в шаблоне есть атрибут "page", но по ID он не нужен
        model.addAttribute("page", null);
        return "list-event-types";
    }

    //+
    @GetMapping("/edit/{id}")
    public String editEventTypeById(@PathVariable("id") Long id, Model model)
            throws RecordNotFoundException {
        EventType eventTypeEntity = typeEventServiceImpl.getEventTypeById(id);
        model.addAttribute("eventType", new EventTypeForm(eventTypeEntity.getId(), eventTypeEntity.getEventType()));
        return "add-edit-Event-Type";
    }

    @GetMapping("/add")
    public String edit(Model model) throws RecordNotFoundException {
        model.addAttribute("eventType", new EventTypeForm());
        return "add-edit-Event-Type";
    }


    //+
    @PostMapping("/add")
    String createNewEventType(@Valid @ModelAttribute("eventType") EventTypeForm eventTypeForm, BindingResult bindingResult, Model model) throws RecordNotFoundException {

        if (bindingResult.hasErrors()) {
            return "add-edit-Event-Type";
        }

        EventType eventTypeEntityExists = typeEventServiceImpl.findEventByName(eventTypeForm.getEventType());
        if (eventTypeEntityExists != null) {
            FieldError error1 = new FieldError("eventType", "eventType","Event Type Already Exists");
            bindingResult.addError(error1);
            return "add-edit-Event-Type";
        } else {
            EventType eventTypeEntity = new EventType();
            eventTypeEntity.setId(eventTypeForm.getId());
            eventTypeEntity.setEventType(eventTypeForm.getEventType());

            EventType updated = typeEventServiceImpl.createOrUpdateEventType(eventTypeEntity);
        }

        return "redirect:/eventTypes";
    }

    @GetMapping("/delete/{id}")
    public String deleteEventTypeById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        typeEventServiceImpl.deleteEventTypeById(id);
        return "redirect:/eventTypes";
    }


}
