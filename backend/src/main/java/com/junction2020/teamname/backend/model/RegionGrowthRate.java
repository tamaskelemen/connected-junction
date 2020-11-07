package com.junction2020.teamname.backend.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.geojson.GeoJsonObject;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@EqualsAndHashCode
@Data @NoArgsConstructor
@Jacksonized @SuperBuilder
public class RegionGrowthRate {

    @Id
    private String name;

    private GeoJsonObject geoJson;

    private Double growthRate;

}
