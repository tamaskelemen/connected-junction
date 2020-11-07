package com.junction.rootkicskacsa.backend.repository;

import com.junction.rootkicskacsa.backend.model.Estate;

import java.util.List;

public interface EstateRepository {
    List<Estate> findAll();
}
