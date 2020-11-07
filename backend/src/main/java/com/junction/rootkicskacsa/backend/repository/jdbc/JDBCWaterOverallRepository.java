package com.junction.rootkicskacsa.backend.repository.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction.rootkicskacsa.backend.model.WaterOverall;
import com.junction.rootkicskacsa.backend.repository.WaterOverallRepository;
import com.junction.rootkicskacsa.backend.repository.jdbc.mapper.WaterOverallRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JDBCWaterOverallRepository implements WaterOverallRepository {

    private final JdbcTemplate jdbc;

    private final ObjectMapper mapper;

    private final WaterOverallRowMapper rowMapper;

    @java.lang.Override
    public List<WaterOverall> findAll() {
        return jdbc.query("SELECT * FROM water_overall;", rowMapper);
    }

    @java.lang.Override
    public Optional<WaterOverall> findByName(String name) {
        WaterOverall waterOverall = jdbc.queryForObject("SELECT * FROM water_overall WHERE name = ?;",
                new Object[]{name},
                rowMapper);
        return Optional.ofNullable(waterOverall);    }

    @java.lang.Override
    @SneakyThrows
    public void save(WaterOverall waterOverall) {
        jdbc.update("INSERT INTO water_overall (name, geo_json, value) VALUES (?, ?::jsonb, ?);",
                waterOverall.getName(),
                mapper.writeValueAsString(waterOverall.getGeoJson()),
                waterOverall.getValue());
    }

    @java.lang.Override
    public void deleteAll() {
        jdbc.execute("DELETE FROM water_overall;");
    }
}
