package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventData;
import org.launchcode.codingevents.models.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Chris Bay
 */
@Controller
@RequestMapping("events")
public class EventController {

    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("title", "All Upcoming Events");
        model.addAttribute("events", EventData.getAllEvents());
        return "events/index";
    }

    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("title", "Create New Event");
        model.addAttribute(new Event());
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event newEvent, Errors errors, Model model) {
        if(errors.hasErrors()){
            model.addAttribute("title", "Create New Event");
            model.addAttribute("errorMsg", "Bad data!");
            return "events/create";
        }
        EventData.add(newEvent);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model){
        model.addAttribute("title", "Delete Events");
        model.addAttribute("events", EventData.getAllEvents());
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam(required = false) Collection<Integer> eventIds){
        if (eventIds != null) {
            for (int id : eventIds) {
                EventData.remove(id);
            }
        }
        return "redirect:";
    }

    @GetMapping("edit/{eventId}")
    public String displayEditForm(Model model, @PathVariable int eventId) {
        Event event = EventData.getById(eventId);
        model.addAttribute("event",event);
        model.addAttribute("title","Edit Event " + event.getName() + " (id=" + event.getId() + ")");
        return "events/edit";
    }

    @PostMapping("edit")
    public String processEditForm(int eventId, String name, String description){
        Event event = EventData.getById(eventId);
        event.setName(name);
        event.setDescription(description);
        return "redirect:";
    }
}
