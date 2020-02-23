package com.tietoevry.soilops.controller;

import com.tietoevry.soilops.dto.DeviceStatisticsResponse;
import com.tietoevry.soilops.dto.DeviceStatusResponse;
import com.tietoevry.soilops.model.DeviceStatus;
import com.tietoevry.soilops.model.Roles;
import com.tietoevry.soilops.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Device statistics", value = "Queries device statistics", produces = "application/json")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @RolesAllowed({Roles.USER})
    @RequestMapping(value = "/api/statistics/devices/{uuid}", method = RequestMethod.GET)
    @ApiOperation(value = "Creates a new device")
    public ResponseEntity<DeviceStatisticsResponse> getStatistics(
            @PathVariable
                    String uuid,
            @ApiIgnore
                    Principal principal) {


        List<DeviceStatus> statistics = statisticsService.getStatistics(uuid);

        DeviceStatisticsResponse deviceStatisticsResponse = new DeviceStatisticsResponse();
        deviceStatisticsResponse.setDeviceUuid(uuid);
        List<DeviceStatusResponse> statusList = statistics.stream().map(deviceStatus -> DeviceStatusResponse.builder()
                .created(deviceStatus.getCreated())
                .light(deviceStatus.getLight())
                .moisture(deviceStatus.getMoisture())
                .temperature(deviceStatus.getTemperature()).build())
            .collect(Collectors.toList());

        deviceStatisticsResponse.setStatusList(statusList);
        return ResponseEntity.ok(deviceStatisticsResponse);
    }
}
