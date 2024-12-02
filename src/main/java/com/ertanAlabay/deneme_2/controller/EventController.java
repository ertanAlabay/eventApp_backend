package com.ertanAlabay.deneme_2.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ertanAlabay.deneme_2.dto.DtoEvent;
import com.ertanAlabay.deneme_2.dto.DtoEventRegistration;
import com.ertanAlabay.deneme_2.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<String> createEvent(@RequestBody DtoEvent eventDTO, @RequestHeader("Authorization") String token) {
        eventService.createEvent(eventDTO, token.replace("Bearer ", ""));
        return ResponseEntity.ok("Event created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEvent(@PathVariable Long id, @RequestBody DtoEvent eventDTO, @RequestHeader("Authorization") String token) {
        eventService.updateEvent(id, eventDTO, token.replace("Bearer ", ""));
        return ResponseEntity.ok("Event updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        eventService.deleteEvent(id, token.replace("Bearer ", ""));
        return ResponseEntity.ok("Event deleted successfully");
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<DtoEvent>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> registerUserToEvent(@RequestBody DtoEventRegistration dto) {
        eventService.registerUserToEvent(dto);
        return ResponseEntity.ok("User registered to event successfully");
    }

    @GetMapping("/{eventId}/participants")
    public ResponseEntity<List<String>> getEventParticipants(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getEventParticipants(eventId));
    }
}
