package com.tietoevry.soilops.repository;

import com.tietoevry.soilops.model.Thing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ThingRepository extends CrudRepository<Thing, Long> {
    List<Thing> findAllByPlaceId(Long id);

    Thing findByUuid(String thingUuid);
}