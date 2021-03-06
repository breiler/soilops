package com.tietoevry.soilops.service;

import com.tietoevry.soilops.dto.DeviceUpdateRequest;
import com.tietoevry.soilops.model.Place;
import com.tietoevry.soilops.model.Device;
import com.tietoevry.soilops.model.User;
import com.tietoevry.soilops.repository.ObservationRepository;
import com.tietoevry.soilops.repository.PlaceRepository;
import com.tietoevry.soilops.repository.DeviceRepository;
import com.tietoevry.soilops.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DeviceService {
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final DeviceRepository deviceRepository;
    private final NameGeneratorService nameGeneratorService;
    private final ObservationRepository observationRepository;

    public DeviceService(PlaceRepository placeRepository, DeviceRepository deviceRepository, UserRepository userRepository, ObservationRepository observationRepository, NameGeneratorService nameGeneratorService) {
        this.placeRepository = placeRepository;
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
        this.nameGeneratorService = nameGeneratorService;
        this.observationRepository = observationRepository;
    }

    public Device create(String username, Device device) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Couldn't find user " + username));

        device.setId(null);
        device.setCreated(LocalDateTime.now());
        device.setUser(user);

        if (StringUtils.isEmpty(device.getUuid())) {
            device.setUuid(UUID.randomUUID().toString());
        }

        if (StringUtils.isEmpty(device.getKey())) {
            device.setKey(RandomStringUtils.randomAlphanumeric(30));
        }

        if (StringUtils.isEmpty(device.getName())) {
            device.setName(nameGeneratorService.generate());
        }

        return deviceRepository.save(device);
    }

    public List<Device> getDevices(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Couldn't find user " + username));
        return deviceRepository.findAllByUserId(user.getId());
    }

    public void delete(String username, String uuid) {
        Optional<Device> deviceOptional = deviceRepository.findByUuid(uuid);
        deviceOptional.ifPresent(device -> {
            if (!device.getUser().getUsername().equalsIgnoreCase(username)) {
                throw new RuntimeException("Not the owner of the device");
            }

            deviceRepository.delete(device);
        });
    }

    public Device update(String username, String uuid, DeviceUpdateRequest deviceUpdateRequest) {
        Device device = deviceRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (!device.getUser().getUsername().equalsIgnoreCase(username)) {
            throw new RuntimeException("Not the owner of the device");
        }

        if (StringUtils.isNotEmpty(deviceUpdateRequest.getName())) {
            device.setName(deviceUpdateRequest.getName());
        }

        return deviceRepository.save(device);
    }

    public Device getDevice(String username, String uuid) {
        Device device = deviceRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (!device.getUser().getUsername().equalsIgnoreCase(username)) {
            throw new RuntimeException("Not the owner of the device");
        }

        return device;
    }
}
