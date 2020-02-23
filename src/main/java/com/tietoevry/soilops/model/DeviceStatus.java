package com.tietoevry.soilops.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class DeviceStatus {
    @Id
    private String id;

    @Column
    private Double temperature;

    @Column
    private Double moisture;

    @Column
    private Double light;

    @Column
    private LocalDateTime created;
}
