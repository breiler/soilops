package com.tietoevry.soilops.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ThingRequest implements Serializable {
    private String name;
    private String placeUuid;
}
