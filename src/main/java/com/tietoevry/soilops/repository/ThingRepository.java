package com.tietoevry.soilops.repository;

import com.tietoevry.soilops.model.Thing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface ThingRepository extends CrudRepository<Thing, Long> {
    Iterable<Thing> findAllByPlaceId(Long id);
}