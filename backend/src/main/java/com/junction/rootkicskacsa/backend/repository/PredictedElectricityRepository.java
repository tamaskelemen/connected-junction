package com.junction.rootkicskacsa.backend.repository;

import com.junction.rootkicskacsa.backend.model.PredictedElectricity;
import com.junction.rootkicskacsa.backend.model.PredictedHeat;
import org.springframework.data.repository.CrudRepository;

public interface PredictedElectricityRepository extends CrudRepository<PredictedElectricity, Long> {
}
