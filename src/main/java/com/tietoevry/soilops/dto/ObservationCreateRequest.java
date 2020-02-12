package com.tietoevry.soilops.dto;

import lombok.Data;

@Data
public class ObservationCreateRequest {
    private Double temperature;
    private Double moisture;
    private Double light;
}
