package com.tietoevry.soilops.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Thing implements Serializable {
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
    private Place place;

    @OneToMany(mappedBy = "thing")
    private List<Observation> observations;

    @Column
    @NotNull
    private String name;
}
