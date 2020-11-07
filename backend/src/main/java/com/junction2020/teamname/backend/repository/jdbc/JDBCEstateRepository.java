package com.junction2020.teamname.backend.repository.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction2020.teamname.backend.model.Estate;
import com.junction2020.teamname.backend.model.RegionGrowthRate;
import com.junction2020.teamname.backend.repository.EstateRepository;
import com.junction2020.teamname.backend.repository.jdbc.mapper.EstateRowMapper;
import com.junction2020.teamname.backend.repository.jdbc.mapper.RegionGrowthRateRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JDBCEstateRepository implements EstateRepository {

    private final JdbcTemplate jdbc;

    private final EstateRowMapper rowMapper;

    @Override
    public List<Estate> findAll() {

        return jdbc.query("SELECT * FROM estates;", rowMapper);
    }
}
