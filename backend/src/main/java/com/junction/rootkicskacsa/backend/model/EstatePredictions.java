package com.junction.rootkicskacsa.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@NoArgsConstructor
@Jacksonized
@SuperBuilder
public class EstatePredictions {

    private PredictedHeat predictedHeat;
    private PredictedWater predictedWater;
    private PredictedElectricity predictedElectricity;

}
