package com.tietoevry.soilops.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ThingDto implements Serializable {
    private String uuid;
    private String name;
}
