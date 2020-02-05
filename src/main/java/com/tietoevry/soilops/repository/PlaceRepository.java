package com.tietoevry.soilops.repository;

import com.tietoevry.soilops.model.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaceRepository extends CrudRepository<Place, Long> {
    Place findByUuidAndUserId(String placeUuid, long userId);

    List<Place> findAllByUserId(long userId);
}