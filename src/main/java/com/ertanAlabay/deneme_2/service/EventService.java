package com.ertanAlabay.deneme_2.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ertanAlabay.deneme_2.dto.DtoEvent;
import com.ertanAlabay.deneme_2.dto.DtoEventRegistration;
import com.ertanAlabay.deneme_2.model.Event;
import com.ertanAlabay.deneme_2.model.User;
import com.ertanAlabay.deneme_2.repository.EventRepository;
import com.ertanAlabay.deneme_2.repository.UserRepository;
import com.ertanAlabay.deneme_2.security.JwtUtil;

import jakarta.transaction.Transactional;


@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public EventService(EventRepository eventRepository, UserRepository userRepository, JwtUtil jwtUtil) {
        this.eventRepository = eventRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public void createEvent(DtoEvent eventDTO, String token) {
        verifyAdminRole(token);

        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setCategory(eventDTO.getCategory());
        event.setDate(eventDTO.getDate());
        event.setLocation(eventDTO.getLocation());

        eventRepository.save(event);
    }

    public void updateEvent(Long id, DtoEvent eventDTO, String token) {
        verifyAdminRole(token);

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setCategory(eventDTO.getCategory());
        event.setDate(eventDTO.getDate());
        event.setLocation(eventDTO.getLocation());

        eventRepository.save(event);
    }

    public void deleteEvent(Long id, String token) {
        verifyAdminRole(token);

        eventRepository.deleteById(id);
    }
    

    private void verifyAdminRole(String token) {
        String role = jwtUtil.extractRole(token);
        if (!role.equals("ADMIN")) {
            throw new RuntimeException("Access Denied: Only ADMIN can manage events");
        }
    }
    
    @Transactional
    public void registerUserToEvent(DtoEventRegistration dto) {
        Long eventId = dto.getEventId();
        Long userId = dto.getUserId();

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // İlişkileri güncelle
        if (!event.getParticipants().contains(user)) {
            event.getParticipants().add(user);
        }
        if (!user.getEvents().contains(event)) {
            user.getEvents().add(event);
        }

        // Veritabanını güncelle
        eventRepository.save(event);
        userRepository.save(user);
    }

    public List<String> getEventParticipants(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Koleksiyonun kopyasını al ve üzerinde işlem yap
        Set<User> participants = new HashSet<>(event.getParticipants());

        return participants.stream()
                .map(user -> event.getName()+ " - " + user.getId() + " - " + user.getFirstName() + " " + user.getLastName() + " - " + user.getEmail())
                .collect(Collectors.toList());
    }
    
    /*@Transactional(readOnly = true)
    public List<String> getEventParticipants(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return event.getParticipants().stream()
                .map(user -> user.getFirstName() + " " + user.getLastName())
                .collect(Collectors.toList());
    }*/

	public List<DtoEvent> getAllEvents() {
		return eventRepository.findAll()
                .stream()
                .map(event -> {
                    DtoEvent dto = new DtoEvent();
                    dto.setName(event.getName());
                    dto.setDescription(event.getDescription());
                    dto.setCategory(event.getCategory());
                    dto.setDate(event.getDate());
                    dto.setLocation(event.getLocation());
                    return dto;
                })
                .collect(Collectors.toList());
	}
}
