package com.junction.rootkicskacsa.backend.repository;

import com.junction.rootkicskacsa.backend.model.RegionGrowthRate;

import java.util.List;
import java.util.Optional;

public interface RegionGrowthRateRepository {

    List<RegionGrowthRate> findAll();
    Optional<RegionGrowthRate> findByName(String name);
    void save(RegionGrowthRate region);
    void deleteAll();

}
