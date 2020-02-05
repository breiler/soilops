package com.tietoevry.soilops.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlaceRequest implements Serializable {
    private String name;
}
