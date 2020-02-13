package com.tietoevry.soilops.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.tietoevry.soilops.dto.DiscoveryResponse;
import com.tietoevry.soilops.model.Device;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DiscoveryService {
    private static final int PIN_LENGTH = 5;
    private static Cache<String, DiscoveryResponse> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(5))
            .build();

    private final DeviceService deviceService;

    public DiscoveryService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public DiscoveryResponse registerDevice() {
        DiscoveryResponse response = new DiscoveryResponse();
        response.setDeviceUuid(UUID.randomUUID().toString());
        response.setPin(generatePin());
        response.setKey(RandomStringUtils.randomAlphanumeric(30));
        cache.put(response.getPin(), response);
        return response;
    }

    private String generatePin() {
        return RandomStringUtils.random(PIN_LENGTH, "12345679ACDEFGHJKLMNPRZVW");
    }

    public Device bindDevice(String username, String pin, String deviceName) {
        DiscoveryResponse discoveryResponse = cache.getIfPresent(pin);
        if(discoveryResponse == null) {
            throw new RuntimeException("No discovery response was found");
        }

        cache.invalidate(pin);

        Device device = Device.builder().created(LocalDateTime.now())
                .name(deviceName)
                .uuid(discoveryResponse.getDeviceUuid())
                .key(discoveryResponse.getKey())
                .build();
        return deviceService.create(username, device);
    }
}
