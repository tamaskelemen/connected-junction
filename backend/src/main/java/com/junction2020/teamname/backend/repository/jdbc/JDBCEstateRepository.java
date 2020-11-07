package com.junction2020.teamname.backend.repository.jdbc;

import com.junction2020.teamname.backend.model.Estate;
import com.junction2020.teamname.backend.repository.EstateRepository;
import com.junction2020.teamname.backend.repository.jdbc.mapper.EstateRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
