package com.junction2020.teamname.backend.repository;

import com.junction2020.teamname.backend.model.RegionGrowthRate;

import java.util.List;
import java.util.Optional;

public interface RegionGrowthRateRepository {

    List<RegionGrowthRate> findAll();
    Optional<RegionGrowthRate> findByName(String name);
    void save(RegionGrowthRate region);
    void deleteAll();

}
