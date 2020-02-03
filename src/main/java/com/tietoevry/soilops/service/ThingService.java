package com.tietoevry.soilops.service;

import com.tietoevry.soilops.model.Place;
import com.tietoevry.soilops.model.Thing;
import com.tietoevry.soilops.repository.PlaceRepository;
import com.tietoevry.soilops.repository.ThingRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ThingService {
    private PlaceRepository placeRepository;
    private ThingRepository thingRepository;
    private NameGeneratorService nameGeneratorService;

    public ThingService(PlaceRepository placeRepository, ThingRepository thingRepository, NameGeneratorService nameGeneratorService) {
        this.placeRepository = placeRepository;
        this.thingRepository = thingRepository;
        this.nameGeneratorService = nameGeneratorService;
    }

    public List<Thing> getThings(String placeUuid) {
        Place place = placeRepository.findByUuid(placeUuid);

        Iterable<Thing> iterable = thingRepository.findAllByPlaceId(place.getId());
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Thing create(Thing thing) {
        thing.setId(null);
        thing.setUuid(UUID.randomUUID().toString());
        if (StringUtils.isEmpty(thing.getName())) {
            thing.setName(nameGeneratorService.generate());
        }

        return thingRepository.save(thing);
    }
}
