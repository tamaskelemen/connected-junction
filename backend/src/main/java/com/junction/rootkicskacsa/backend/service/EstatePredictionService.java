package com.junction.rootkicskacsa.backend.service;

import com.junction.rootkicskacsa.backend.model.EstatePredictions;
import com.junction.rootkicskacsa.backend.repository.PredictedElectricityRepository;
import com.junction.rootkicskacsa.backend.repository.PredictedHeatRepository;
import com.junction.rootkicskacsa.backend.repository.PredictedWaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstatePredictionService {

    private final PredictedHeatRepository predictedHeatRepository;
    private final PredictedWaterRepository predictedWaterRepository;
    private final PredictedElectricityRepository predictedElectricityRepository;

    public EstatePredictions getEstatePredictions(@NonNull Long objectId) {
        return EstatePredictions.builder()
          .predictedHeat(predictedHeatRepository.findById(objectId).orElse(null))
          .predictedWater(predictedWaterRepository.findById(objectId).orElse(null))
          .predictedElectricity(predictedElectricityRepository.findById(objectId).orElse(null))
          .build();
    }
}
