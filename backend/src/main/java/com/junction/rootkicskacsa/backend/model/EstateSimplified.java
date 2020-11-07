package com.junction.rootkicskacsa.backend.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data @NoArgsConstructor
@Jacksonized @SuperBuilder
public class EstateSimplified {

    private Long id;
    private Long objectId;

    @JsonRawValue
    private String housingCoating;

    @JsonRawValue
    private String housingNumberOfRooms;

    @JsonRawValue
    private String housingTotalArea;

    @JsonRawValue
    private String buildingEnergyClass;

    @JsonRawValue
    private String category;

    @JsonRawValue
    private String objectType;

    @JsonRawValue
    private String salesPrice;

    @JsonRawValue
    private String renovations;

    @JsonRawValue
    private String rentPerMonth;

    @JsonRawValue
    private String buildingPlanSituation;

    @JsonRawValue
    private String unencumberedSalesPrice;

    @JsonRawValue
    private String coordinates;

}