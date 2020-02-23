package com.tietoevry.soilops.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DeviceStatusResponse {
    private LocalDateTime created;

    private Double temperature;

    private Double moisture;

    private Double light;
}
