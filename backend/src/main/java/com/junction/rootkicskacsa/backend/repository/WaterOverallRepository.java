package com.junction.rootkicskacsa.backend.repository;

import com.junction.rootkicskacsa.backend.model.WaterOverall;


import java.util.List;
import java.util.Optional;

public interface WaterOverallRepository {
    List<WaterOverall> findAll();
    Optional<WaterOverall> findByName(String name);
    void save(WaterOverall region);
    void deleteAll();
}
