package com.tietoevry.soilops.controller;

import com.tietoevry.soilops.dto.JwtResponse;
import com.tietoevry.soilops.dto.ObservationResponse;
import com.tietoevry.soilops.dto.ThingRequest;
import com.tietoevry.soilops.dto.ThingResponse;
import com.tietoevry.soilops.model.Observation;
import com.tietoevry.soilops.model.Roles;
import com.tietoevry.soilops.model.Thing;
import com.tietoevry.soilops.auth.jwt.JwtTokenService;
import com.tietoevry.soilops.service.ObservationService;
import com.tietoevry.soilops.service.ThingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Things", produces = "application/json")
public class ThingController {

    private final JwtTokenService jwtTokenService;
    private final ObservationService observationService;
    private final ThingService thingService;
    private final ModelMapper modelMapper;


    public ThingController(ThingService thingService, JwtTokenService jwtTokenService, ObservationService observationService) {
        this.thingService = thingService;
        this.jwtTokenService = jwtTokenService;
        this.observationService = observationService;
        this.modelMapper = new ModelMapper();
    }

    @RolesAllowed({Roles.USER})
    @RequestMapping(value = "/api/things", method = RequestMethod.GET)
    @ApiOperation(value = "Returns all registered things", response = ThingResponse[].class)
    public ResponseEntity<List<ThingResponse>> getThings(
            @ApiIgnore
                    Principal principal) {

        List<Thing> things = thingService.getThings(principal.getName());
        List<ThingResponse> mappedThings = things.stream()
                .map(this::mapThing)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mappedThings);
    }

    @RolesAllowed({Roles.USER})
    @RequestMapping(value = "/api/things", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new thing at the given place", response = ThingResponse.class)
    public ResponseEntity<ThingResponse> createThing(
            @ApiIgnore
                    Principal principal,
            @RequestBody
                    ThingRequest thingDto) {

        ModelMapper modelMapper = new ModelMapper();
        Thing thing = modelMapper.map(thingDto, Thing.class);
        thing = thingService.create(principal.getName(), thingDto.getPlaceUuid(), thing);
        return ResponseEntity.ok(mapThing(thing));
    }

    @RolesAllowed({Roles.USER})
    @RequestMapping(value = "/api/things/{uuid}/token", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a token for a thing", notes = "Creates a token for a thing that can be used for creating observations", response = JwtResponse.class)
    public ResponseEntity<JwtResponse> createThingToken(
            @ApiIgnore
                    Principal principal,
            @PathVariable
                    String uuid) {
        final String token = jwtTokenService.generateThingToken(principal.getName(), uuid);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RolesAllowed({Roles.USER})
    @RequestMapping(value = "/api/things/{uuid}/observations", method = RequestMethod.GET)
    @ApiOperation(value = "Fetches all observations for the thing", response = ObservationResponse[].class, responseContainer = "List")
    public ResponseEntity<List<ObservationResponse>> getObservations(
            @ApiIgnore
                    Principal principal,
            @PathVariable
                    String uuid) {
        List<Observation> observations = observationService.findAll(principal.getName(), uuid);

        ModelMapper modelMapper = new ModelMapper();
        return ResponseEntity.ok(Arrays.asList(modelMapper.map(observations, ObservationResponse[].class)));
    }

    @RolesAllowed({Roles.USER})
    @RequestMapping(value = "/api/things/{uuid}/observation", method = RequestMethod.GET)
    @ApiOperation(value = "Fetches the latest observation for a thing", response = ObservationResponse.class)
    public ResponseEntity<ObservationResponse> getLatestObservation(
            @ApiIgnore
                    Principal principal,
            @PathVariable
                    String uuid) {
        List<Observation> observations = observationService.findAll(principal.getName(), uuid);
        return ResponseEntity.ok(modelMapper.map(observations.get(0), ObservationResponse.class));
    }


    private ThingResponse mapThing(Thing thing) {
        ThingResponse thingResponse = modelMapper.map(thing, ThingResponse.class);
        thing.getObservations().stream()
                .max(Comparator.comparing(Observation::getCreated))
                .ifPresent(o -> thingResponse.setLatestObservation(modelMapper.map(o, ObservationResponse.class)));

        return thingResponse;
    }
}
