package com.junction2020.teamname.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.geojson.GeoJsonObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@EqualsAndHashCode
@Data @NoArgsConstructor
@Jacksonized @SuperBuilder
public class Estate {
    @Id
    private long id;

    @Column(columnDefinition="jsonb")
    private String data;
}
