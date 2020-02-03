package com.tietoevry.soilops.controller;

import com.tietoevry.soilops.dto.ThingDto;
import com.tietoevry.soilops.model.Thing;
import com.tietoevry.soilops.service.NameGeneratorService;
import com.tietoevry.soilops.service.ThingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Things", produces = "application/json")
public class ThingController {

    private ThingService thingService;

    public ThingController(ThingService thingService) {
        this.thingService = thingService;
    }

    @RequestMapping(value = "/api/places/{placeUuid}/things", method = RequestMethod.GET)
    @ApiOperation(value = "Returns all registered things", response = ThingDto[].class)
    public ResponseEntity<List<ThingDto>> getThings(
            @PathVariable("placeUuid")
            String placeUuid) {
        List<Thing> things = thingService.getThings(placeUuid);

        ModelMapper modelMapper = new ModelMapper();
        List<ThingDto> mappedThings = things.stream()
                .map(t -> modelMapper.map(t, ThingDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(mappedThings);
    }

    @RequestMapping(value = "/api/things", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new thing", response = ThingDto.class)
    public ResponseEntity<ThingDto> createThing(@RequestBody ThingDto thingDto) {

        ModelMapper modelMapper = new ModelMapper();
        Thing thing = modelMapper.map(thingDto, Thing.class);
        thing = thingService.create(thing);

        return ResponseEntity.ok(modelMapper.map(thing, ThingDto.class));
    }
}
