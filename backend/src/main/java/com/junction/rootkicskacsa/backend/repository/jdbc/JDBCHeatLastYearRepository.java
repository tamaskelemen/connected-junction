package com.junction.rootkicskacsa.backend.repository.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction.rootkicskacsa.backend.model.HeatLastYear;
import com.junction.rootkicskacsa.backend.repository.HeatLastYearRepository;
import com.junction.rootkicskacsa.backend.repository.jdbc.mapper.HeatLastYearRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JDBCHeatLastYearRepository implements HeatLastYearRepository {
    private final JdbcTemplate jdbc;

    private final ObjectMapper mapper;

    private final HeatLastYearRowMapper rowMapper;

    @java.lang.Override
    public List<HeatLastYear> findAll() {
        return jdbc.query("SELECT * FROM heat_last_year;", rowMapper);
    }

    @java.lang.Override
    public Optional<HeatLastYear> findByName(String name) {
        HeatLastYear heatLastYear = jdbc.queryForObject("SELECT * FROM heat_last_year WHERE name = ?;",
                new Object[]{name},
                rowMapper);
        return Optional.ofNullable(heatLastYear);    }

    @java.lang.Override
    @SneakyThrows
    public void save(HeatLastYear heatLastYear) {
        jdbc.update("INSERT INTO heat_last_year (name, geo_json, value) VALUES (?, ?::jsonb, ?);",
                heatLastYear.getName(),
                mapper.writeValueAsString(heatLastYear.getGeoJson()),
                heatLastYear.getValue());
    }

    @java.lang.Override
    public void deleteAll() {
        jdbc.execute("DELETE FROM heat_last_year;");
    }
}
