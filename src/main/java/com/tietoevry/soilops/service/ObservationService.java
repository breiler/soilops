package com.tietoevry.soilops.service;

import com.tietoevry.soilops.eventbus.EventBusWebSocketHandler;
import com.tietoevry.soilops.model.Device;
import com.tietoevry.soilops.model.Observation;
import com.tietoevry.soilops.repository.ObservationRepository;
import com.tietoevry.soilops.repository.DeviceRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ObservationService {
    private final ObservationRepository observationRepository;
    private final DeviceRepository deviceRepository;
    private final EventBusWebSocketHandler eventBusWebSocketHandler;

    public ObservationService(ObservationRepository observationRepository, DeviceRepository deviceRepository, EventBusWebSocketHandler eventBusWebSocketHandler) {
        this.deviceRepository = deviceRepository;
        this.observationRepository = observationRepository;
        this.eventBusWebSocketHandler = eventBusWebSocketHandler;
    }

    public Observation create( String thingUuid, String thingKey, Observation observation) {
        Device device = deviceRepository.findByUuid(thingUuid);
        if(StringUtils.isEmpty(device.getKey()) || !StringUtils.equals(thingKey, device.getKey())) {
            throw new AccessDeniedException("Not allowed to access thing with given key");
        }

        observation.setId(null);
        observation.setUuid(UUID.randomUUID().toString());
        observation.setDevice(device);
        observation.setCreated(LocalDateTime.now());
        observation = observationRepository.save(observation);

        this.eventBusWebSocketHandler.sendObservation(observation);
        return observation;
    }

    public List<Observation> findAll(String username, String thingUuid) {
        Device device = deviceRepository.findByUuid(thingUuid);
        if(device == null) {
            throw new RuntimeException("Couldn't find resource");
        }

        if (!device.getPlace().getUser().getUsername().equalsIgnoreCase(username)) {
            throw new RuntimeException("Couldn't find thing " + thingUuid + " for user " + username);
        }

        return observationRepository.findAllByDeviceId(device.getId(), Sort.by(Sort.Direction.DESC, "created"));
    }
}
