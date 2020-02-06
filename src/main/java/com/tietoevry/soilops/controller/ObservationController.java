package com.tietoevry.soilops.controller;

import com.tietoevry.soilops.dto.*;
import com.tietoevry.soilops.model.Observation;
import com.tietoevry.soilops.model.Roles;
import com.tietoevry.soilops.auth.UserDetails;
import com.tietoevry.soilops.service.ObservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RestController
@Api(tags = "Observations", produces = "application/json")
public class ObservationController {

    private ObservationService observationService;

    public ObservationController(ObservationService observationService) {
        this.observationService = observationService;
    }

    @RolesAllowed({Roles.THING})
    @RequestMapping(value = "/api/observations", method = RequestMethod.POST)
    @ApiOperation(value = "Creates an observation for a thing", notes="Creates an observation for a thing. This method requires a specific token for a thing.", response = ObservationResponse.class)
    public ResponseEntity<ObservationResponse> createObservation(
            @ApiIgnore
                    Principal principal,
            @RequestBody
                    ObservationRequest observationDto) {
        String thingUuid = getThingUuidFromUser(principal);
        if (StringUtils.isEmpty(thingUuid)) {
            throw new IllegalArgumentException("The provided token is not for a thing.");
        }

        ModelMapper modelMapper = new ModelMapper();
        Observation observation = modelMapper.map(observationDto, Observation.class);
        observation = observationService.create(principal.getName(), thingUuid, observation);
        return ResponseEntity.ok(modelMapper.map(observation, ObservationResponse.class));
    }

    private String getThingUuidFromUser(@ApiIgnore Principal principal) {
        String thingUuid = null;
        if (principal instanceof UsernamePasswordAuthenticationToken && ((UsernamePasswordAuthenticationToken) principal).getPrincipal() instanceof UserDetails) {
            thingUuid = ((UserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getThingUuid();
        }
        return thingUuid;
    }
}
