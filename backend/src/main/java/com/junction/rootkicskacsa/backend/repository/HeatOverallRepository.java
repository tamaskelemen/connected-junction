package com.junction.rootkicskacsa.backend.repository;

import com.junction.rootkicskacsa.backend.model.HeatOverall;


import java.util.List;
import java.util.Optional;

public interface HeatOverallRepository {
    List<HeatOverall> findAll();
    Optional<HeatOverall> findByName(String name);
    void save(HeatOverall region);
    void deleteAll();
}
