package com.ajunior.tdd.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajunior.tdd.dto.EventDTO;
import com.ajunior.tdd.entities.City;
import com.ajunior.tdd.entities.Event;
import com.ajunior.tdd.repositories.EventRepository;
import com.ajunior.tdd.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {
	
	@Autowired
	private EventRepository repository;

	@Transactional(readOnly = true)
	public Page<EventDTO> findAll(Pageable pageable){
		Page<Event> page = repository.findAll(pageable);
		return page.map(x -> new EventDTO(x));
	}
	
	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
			Event entity = repository.getOne(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new EventDTO(entity);
		} 
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	private void copyDtoToEntity(EventDTO dto, Event entity) {
		entity.setName(dto.getName());
		entity.setDate(dto.getDate());
		entity.setUrl(dto.getUrl());
		City city = new City(dto.getCityId(), null);
		entity.setCity(city);
	}
}
