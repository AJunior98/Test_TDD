package com.ajunior.tdd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ajunior.tdd.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
	
}
