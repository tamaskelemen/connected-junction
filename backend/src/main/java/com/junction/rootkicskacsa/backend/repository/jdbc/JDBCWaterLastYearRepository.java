package com.junction.rootkicskacsa.backend.repository.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction.rootkicskacsa.backend.model.WaterLastYear;
import com.junction.rootkicskacsa.backend.repository.WaterLastYearRepository;
import com.junction.rootkicskacsa.backend.repository.jdbc.mapper.WaterLastYearRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JDBCWaterLastYearRepository implements WaterLastYearRepository {
    private final JdbcTemplate jdbc;

    private final ObjectMapper mapper;

    private final WaterLastYearRowMapper rowMapper;

    @java.lang.Override
    public List<WaterLastYear> findAll() {
        return jdbc.query("SELECT * FROM water_last_year;", rowMapper);
    }

    @java.lang.Override
    public Optional<WaterLastYear> findByName(String name) {
        WaterLastYear waterLastYear = jdbc.queryForObject("SELECT * FROM water_last_year WHERE name = ?;",
                new Object[]{name},
                rowMapper);
        return Optional.ofNullable(waterLastYear);
    }

    @java.lang.Override
    @SneakyThrows
    public void save(WaterLastYear waterLastYear) {
        jdbc.update("INSERT INTO water_last_year (name, geo_json, value) VALUES (?, ?::jsonb, ?);",
                waterLastYear.getName(),
                mapper.writeValueAsString(waterLastYear.getGeoJson()),
                waterLastYear.getValue());
    }

    @java.lang.Override
    public void deleteAll() {
        jdbc.execute("DELETE FROM water_last_year;");
    }

}
