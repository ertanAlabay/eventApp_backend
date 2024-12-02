package com.ertanAlabay.deneme_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ertanAlabay.deneme_2.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
