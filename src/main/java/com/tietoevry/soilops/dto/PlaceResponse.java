package com.tietoevry.soilops.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PlaceResponse extends PlaceRequest implements Serializable {
    private String uuid;
    private LocalDateTime created;
}
