package com.junction.rootkicskacsa.backend.repository;

import com.junction.rootkicskacsa.backend.model.WaterLastYear;

import java.util.List;
import java.util.Optional;

public interface WaterLastYearRepository {
    List<WaterLastYear> findAll();
    Optional<WaterLastYear> findByName(String name);
    void save(WaterLastYear region);
    void deleteAll();
}
