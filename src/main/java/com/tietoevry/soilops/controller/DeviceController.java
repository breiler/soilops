package com.tietoevry.soilops.controller;

import com.tietoevry.soilops.dto.*;
import com.tietoevry.soilops.model.Device;
import com.tietoevry.soilops.model.Observation;
import com.tietoevry.soilops.model.Roles;
import com.tietoevry.soilops.service.ObservationService;
import com.tietoevry.soilops.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Devices", value = "Manages the devices", produces = "application/json")
public class DeviceController {

    private final ObservationService observationService;
    private final DeviceService deviceService;
    private final ModelMapper modelMapper;

    public DeviceController(DeviceService deviceService, ObservationService observationService) {
        this.deviceService = deviceService;
        this.observationService = observationService;
        this.modelMapper = new ModelMapper();
    }

    @RolesAllowed({Roles.USER})
    @RequestMapping(value = "/api/devices", method = RequestMethod.GET)
    @ApiOperation(value = "Returns all registered devices for the authenticated user", response = DeviceResponse[].class)
    public ResponseEntity<List<DeviceResponse>> getDevices(
            @ApiIgnore
                    Principal principal) {

        List<Device> devices = deviceService.getDevices(principal.getName());
        List<DeviceResponse> result = devices.stream()
                .map(this::mapDevice)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @RolesAllowed({Roles.USER})
    @RequestMapping(value = "/api/devices", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new device", response = DeviceResponse.class)
    public ResponseEntity<DeviceResponse> createDevice(
            @ApiIgnore
                    Principal principal,
            @RequestBody
                    DeviceCreateRequest deviceDto) {

        ModelMapper modelMapper = new ModelMapper();
        Device device = modelMapper.map(deviceDto, Device.class);
        device = deviceService.create(principal.getName(), device);
        return ResponseEntity.ok(mapDevice(device));
    }

    @RolesAllowed({Roles.USER})
    @RequestMapping(value = "/api/devices/{uuid}/observations", method = RequestMethod.GET)
    @ApiOperation(value = "Fetches all observations for the device", response = ObservationResponse[].class, responseContainer = "List")
    public ResponseEntity<List<ObservationResponse>> getObservations(
            @ApiIgnore
                    Principal principal,
            @PathVariable
                    String uuid) {
        List<Observation> observations = observationService.findAll(principal.getName(), uuid);

        ModelMapper modelMapper = new ModelMapper();
        return ResponseEntity.ok(Arrays.asList(modelMapper.map(observations, ObservationResponse[].class)));
    }

    @RequestMapping(value = "/api/devices/{uuid}/observations", method = RequestMethod.POST)
    @ApiOperation(value = "Creates an observation for a device", notes = "Creates an observation for a device.", response = ObservationResponse.class)
    public ResponseEntity<ObservationResponse> createObservation(
            @ApiParam(value = "The unique id of the device")
            @PathVariable
                    String uuid,
            @ApiParam(value = "A key for the device for authentication")
            @RequestHeader(value = "X-Device-Key")
                    String key,
            @RequestBody
                    ObservationCreateRequest observationDto) {
        ModelMapper modelMapper = new ModelMapper();
        Observation observation = modelMapper.map(observationDto, Observation.class);
        observation.setUuid(uuid);
        observation = observationService.create(uuid, key, observation);
        return ResponseEntity.ok(modelMapper.map(observation, ObservationResponse.class));
    }


    private DeviceResponse mapDevice(Device device) {
        DeviceResponse response = modelMapper.map(device, DeviceResponse.class);
        if (!CollectionUtils.isEmpty(device.getObservations())) {
            device.getObservations().stream()
                    .max(Comparator.comparing(Observation::getCreated))
                    .ifPresent(o -> response.setLatestObservation(modelMapper.map(o, ObservationResponse.class)));
        }
        return response;
    }
}
