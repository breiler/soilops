package com.tietoevry.soilops.controller;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.tietoevry.soilops.dto.DeviceResponse;
import com.tietoevry.soilops.dto.DiscoveryResponse;
import com.tietoevry.soilops.dto.DiscoveryUpdateRequest;
import com.tietoevry.soilops.dto.PlaceResponse;
import com.tietoevry.soilops.model.Device;
import com.tietoevry.soilops.model.Roles;
import com.tietoevry.soilops.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@Api(tags = "Discovery", value = "Manages the discovery of new devices", produces = "application/json")
public class DiscoveryController {

    private static final int PIN_LENGTH = 5;
    private static Cache<String, DiscoveryResponse> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(5))
            .build();

    private final DeviceService deviceService;

    public DiscoveryController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @RequestMapping(value = "/api/discovery", method = RequestMethod.POST)
    @ApiOperation(value = "Registers a new device", notes = "Registers a discovered device and generates a pin code that can be used to bind it to a user and place.", response = DiscoveryResponse.class)
    public ResponseEntity<DiscoveryResponse> createDiscovery() {
        DiscoveryResponse response = new DiscoveryResponse();
        response.setDeviceUuid(UUID.randomUUID().toString());
        response.setPin(generatePin());
        cache.put(response.getPin(), response);
        return ResponseEntity.ok(response);
    }

    private String generatePin() {
        return RandomStringUtils.random(PIN_LENGTH, "12345679ACDEFGHJKLMNPRZVW");
    }

    @RolesAllowed({Roles.USER})
    @RequestMapping(value = "/api/discovery", method = RequestMethod.PUT)
    @ApiOperation(value = "Binds a device to a user and saves it to a place", notes = "Binds a discovered device to a user and place. This will make it available for registering observations.", response = DeviceResponse.class)
    public ResponseEntity<DeviceResponse>  bindDiscovery(
            @ApiIgnore
                    Principal principal,
            @RequestBody
            DiscoveryUpdateRequest discoveryUpdateRequest) {
        DiscoveryResponse discoveryResponse = cache.getIfPresent(discoveryUpdateRequest.getPin());
        if(discoveryResponse == null) {
            throw new RuntimeException("No discovery response was found");
        }

        cache.invalidate(discoveryUpdateRequest.getPin());

        Device device = Device.builder().created(LocalDateTime.now())
                .name(discoveryUpdateRequest.getName())
                .uuid(discoveryResponse.getDeviceUuid())
                .build();

        ModelMapper modelMapper = new ModelMapper();
        device = deviceService.create(principal.getName(), discoveryUpdateRequest.getPlaceUuid(), device);
        return ResponseEntity.ok(modelMapper.map(device, DeviceResponse.class));
    }
}
