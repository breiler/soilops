package com.tietoevry.soilops.controller;

import com.tietoevry.soilops.dto.PlaceResponse;
import com.tietoevry.soilops.dto.ThingResponse;
import com.tietoevry.soilops.model.Place;
import com.tietoevry.soilops.service.PlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Places", produces = "application/json")
public class PlaceController {

    private PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @RequestMapping(value = "/api/places", method = RequestMethod.GET)
    @ApiOperation(value = "Returns all registered places", response = PlaceResponse[].class)
    public ResponseEntity<List<PlaceResponse>> fetchAll(
            @ApiIgnore Principal principal) {

        List<Place> places = placeService.findAllByUsername(principal.getName());
        ModelMapper modelMapper = new ModelMapper();
        List<PlaceResponse> mappedThings = places.stream()
                .map(t -> modelMapper.map(t, PlaceResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(mappedThings);
    }

    @RequestMapping(value = "/api/places", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new place", response = ThingResponse.class)
    public ResponseEntity<PlaceResponse> create(
            @ApiIgnore
                    Principal principal,
            @RequestBody
                    PlaceResponse placeDto) {

        ModelMapper modelMapper = new ModelMapper();
        Place place = modelMapper.map(placeDto, Place.class);
        place = placeService.create(principal.getName(), place);

        return ResponseEntity.ok(modelMapper.map(place, PlaceResponse.class));
    }
}
