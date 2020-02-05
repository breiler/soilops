package com.tietoevry.soilops.service;

import com.tietoevry.soilops.eventbus.EventBusWebSocketHandler;
import com.tietoevry.soilops.model.Observation;
import com.tietoevry.soilops.model.Thing;
import com.tietoevry.soilops.repository.ObservationRepository;
import com.tietoevry.soilops.repository.ThingRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ObservationService {
    private final ObservationRepository observationRepository;
    private final ThingRepository thingRepository;
    private final EventBusWebSocketHandler eventBusWebSocketHandler;

    public ObservationService(ObservationRepository observationRepository, ThingRepository thingRepository, EventBusWebSocketHandler eventBusWebSocketHandler) {
        this.thingRepository = thingRepository;
        this.observationRepository = observationRepository;
        this.eventBusWebSocketHandler = eventBusWebSocketHandler;
        ;
    }

    public Observation create(String username, String thingUuid, Observation observation) {
        Thing thing = thingRepository.findByUuid(thingUuid);

        if (!thing.getPlace().getUser().getUsername().equalsIgnoreCase(username)) {
            throw new RuntimeException("Couldn't find thing " + thingUuid + " for user " + username);
        }

        observation.setId(null);
        observation.setUuid(UUID.randomUUID().toString());
        observation.setThing(thing);
        observation.setCreated(LocalDateTime.now());
        observation = observationRepository.save(observation);

        this.eventBusWebSocketHandler.sendObservation(observation);
        return observation;
    }

    public List<Observation> findAll(String username, String thingUuid) {
        Thing thing = thingRepository.findByUuid(thingUuid);

        if (!thing.getPlace().getUser().getUsername().equalsIgnoreCase(username)) {
            throw new RuntimeException("Couldn't find thing " + thingUuid + " for user " + username);
        }

        return observationRepository.findAllByThingId(thing.getId(), Sort.by(Sort.Direction.DESC, "created"));
    }
}
