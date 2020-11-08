package com.junction.rootkicskacsa.backend.repository;

import com.junction.rootkicskacsa.backend.model.ElectricityLastyear;

import java.util.List;
import java.util.Optional;

public interface ElectricityLastyearRepository {
    List<ElectricityLastyear> findAll();
    Optional<ElectricityLastyear> findByName(String name);
    void save(ElectricityLastyear region);
    void deleteAll();
}
