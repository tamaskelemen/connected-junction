package com.junction.rootkicskacsa.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data @NoArgsConstructor
@Jacksonized @SuperBuilder
public class PredictedWater {

    @Id
    private Long estateObjectId;

    private Double value;

}
