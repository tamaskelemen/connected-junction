package com.junction2020.teamname.backend.repository;

import com.junction2020.teamname.backend.model.Estate;

import java.util.List;
import java.util.Optional;

public interface EstateRepository {
    List<Estate> findAll();

}
