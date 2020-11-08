package com.junction.rootkicskacsa.backend.repository;

import com.junction.rootkicskacsa.backend.model.HeatLastYear;

import java.util.List;
import java.util.Optional;

public interface HeatLastYearRepository {
    List<HeatLastYear> findAll();
    Optional<HeatLastYear> findByName(String name);
    void save(HeatLastYear region);
    void deleteAll();
}
