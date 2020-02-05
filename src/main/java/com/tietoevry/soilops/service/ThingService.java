package com.tietoevry.soilops.service;

import com.tietoevry.soilops.model.Place;
import com.tietoevry.soilops.model.Thing;
import com.tietoevry.soilops.model.User;
import com.tietoevry.soilops.repository.ObservationRepository;
import com.tietoevry.soilops.repository.PlaceRepository;
import com.tietoevry.soilops.repository.ThingRepository;
import com.tietoevry.soilops.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ThingService {
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final ThingRepository thingRepository;
    private final NameGeneratorService nameGeneratorService;
    private final ObservationRepository observationRepository;

    public ThingService(PlaceRepository placeRepository, ThingRepository thingRepository, UserRepository userRepository, ObservationRepository observationRepository, NameGeneratorService nameGeneratorService) {
        this.placeRepository = placeRepository;
        this.thingRepository = thingRepository;
        this.userRepository = userRepository;
        this.nameGeneratorService = nameGeneratorService;
        this.observationRepository = observationRepository;
    }

    public Thing create(String username, String placeUuid, Thing thing) {
        User user = userRepository.findByUsername(username);
        Place place = placeRepository.findByUuidAndUserId(placeUuid, user.getId());
        thing.setId(null);
        thing.setCreated(LocalDateTime.now());
        thing.setPlace(place);
        thing.setUuid(UUID.randomUUID().toString());
        if (StringUtils.isEmpty(thing.getName())) {
            thing.setName(nameGeneratorService.generate());
        }

        return thingRepository.save(thing);
    }

    public List<Thing> getThings(String username) {
        User user = userRepository.findByUsername(username);
        Iterable<Place> places = placeRepository.findAllByUserId(user.getId());
        return StreamSupport.stream(places.spliterator(), false)
                .map(place -> thingRepository.findAllByPlaceId(place.getId()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
