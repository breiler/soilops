package com.tietoevry.soilops.controller;

import com.tietoevry.soilops.dto.PlaceDto;
import com.tietoevry.soilops.dto.ThingDto;
import com.tietoevry.soilops.model.Place;
import com.tietoevry.soilops.model.Thing;
import com.tietoevry.soilops.service.PlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiOperation(value = "Returns all registered places", response = PlaceDto[].class)
    public ResponseEntity<List<PlaceDto>> fetchAll() {
        List<Place> places = placeService.fetchAll();

        ModelMapper modelMapper = new ModelMapper();
        List<PlaceDto> mappedThings = places.stream()
                .map(t -> modelMapper.map(t, PlaceDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(mappedThings);
    }

    @RequestMapping(value = "/api/places", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new place", response = ThingDto.class)
    public ResponseEntity<PlaceDto> create(@RequestBody PlaceDto placeDto) {

        ModelMapper modelMapper = new ModelMapper();
        Place thing = modelMapper.map(placeDto, Place.class);
        thing = placeService.create(thing);

        return ResponseEntity.ok(modelMapper.map(thing, PlaceDto.class));
    }
}
