package com.tietoevry.soilops.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Observation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String uuid;

    @Column
    @NotNull
    private LocalDateTime created;

    @ManyToOne
    private Device device;

    @Column
    @NotNull
    private Double temperature;

    @Column
    @NotNull
    private Double moisture;

    @Column
    @NotNull
    private Double light;
}
