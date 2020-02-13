package com.tietoevry.soilops.controller;

import com.tietoevry.soilops.dto.DeviceResponse;
import com.tietoevry.soilops.dto.DiscoveryResponse;
import com.tietoevry.soilops.dto.DiscoveryUpdateRequest;
import com.tietoevry.soilops.model.Device;
import com.tietoevry.soilops.model.Roles;
import com.tietoevry.soilops.service.DiscoveryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RestController
@Api(tags = "Discovery", value = "Manages the discovery of new devices", produces = "application/json")
public class DiscoveryController {


    private final DiscoveryService discoveryService;

    public DiscoveryController(DiscoveryService discoveryService) {
        this.discoveryService = discoveryService;
    }

    @RequestMapping(value = "/api/discovery", method = RequestMethod.POST)
    @ApiOperation(value = "Registers a new device", notes = "Registers a discovered device and generates a pin code that can be used to bind it to a user and place.", response = DiscoveryResponse.class)
    public ResponseEntity<DiscoveryResponse> createDiscovery() {
        return ResponseEntity.ok(discoveryService.registerDevice());
    }


    @RolesAllowed({Roles.USER})
    @RequestMapping(value = "/api/discovery", method = RequestMethod.PUT)
    @ApiOperation(value = "Binds a device to a user and saves it to a place", notes = "Binds a discovered device to a user and place. This will make it available for registering observations.", response = DeviceResponse.class)
    public ResponseEntity<DeviceResponse> bindDiscovery(
            @ApiIgnore
                    Principal principal,
            @RequestBody
                    DiscoveryUpdateRequest discoveryUpdateRequest) {
        Device device = discoveryService.bindDevice(principal.getName(), discoveryUpdateRequest.getPin(), discoveryUpdateRequest.getName());
        ModelMapper modelMapper = new ModelMapper();
        return ResponseEntity.ok(modelMapper.map(device, DeviceResponse.class));
    }
}
