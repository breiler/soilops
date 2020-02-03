package com.tietoevry.soilops.service;

import com.tietoevry.soilops.model.Place;
import com.tietoevry.soilops.model.Thing;
import com.tietoevry.soilops.repository.PlaceRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PlaceService {
    private PlaceRepository placeRepository;
    private NameGeneratorService nameGeneratorService;

    public PlaceService(PlaceRepository placeRepository, NameGeneratorService nameGeneratorService) {
        this.placeRepository = placeRepository;
        this.nameGeneratorService = nameGeneratorService;
    }

    public List<Place> fetchAll() {
        Iterable<Place> iterable = placeRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Place create(Place place) {
        place.setId(null);
        place.setUuid(UUID.randomUUID().toString());
        if (StringUtils.isEmpty(place.getName())) {
            place.setName(nameGeneratorService.generate());
        }

        return placeRepository.save(place);
    }
}
