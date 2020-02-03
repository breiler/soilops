package com.tietoevry.soilops.repository;

import com.tietoevry.soilops.model.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface PlaceRepository extends CrudRepository<Place, Long> {
    Place findByUuid(String placeUuid);
}