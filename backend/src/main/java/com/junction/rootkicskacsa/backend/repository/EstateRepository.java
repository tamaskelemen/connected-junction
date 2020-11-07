package com.junction.rootkicskacsa.backend.repository;

import com.junction.rootkicskacsa.backend.model.Estate;
import com.junction.rootkicskacsa.backend.model.EstateSimplified;

import java.util.List;

public interface EstateRepository {
    List<Estate> findAll();
    List<EstateSimplified> findAllRelevant();
}
