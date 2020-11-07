package com.junction2020.teamname.backend.repository;

import com.junction2020.teamname.backend.model.Estate;

import java.util.List;

public interface EstateRepository {
    List<Estate> findAll();
}
