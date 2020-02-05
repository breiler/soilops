package com.tietoevry.soilops.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponse implements Serializable {
    private final String token;
}