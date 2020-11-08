package com.junction.rootkicskacsa.backend.repository;

import com.junction.rootkicskacsa.backend.model.ElectricityOverall;

import java.util.List;
import java.util.Optional;

public interface ElectricityOverallRepository {
    List<ElectricityOverall> findAll();
    Optional<ElectricityOverall> findByName(String name);
    void save(ElectricityOverall region);
    void deleteAll();
}
