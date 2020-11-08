package com.junction.rootkicskacsa.backend.repository.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction.rootkicskacsa.backend.model.HeatOverall;
import com.junction.rootkicskacsa.backend.repository.HeatOverallRepository;
import com.junction.rootkicskacsa.backend.repository.jdbc.mapper.HeatOverallRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JDBCHeatOverallRepository implements HeatOverallRepository {

    private final JdbcTemplate jdbc;

    private final ObjectMapper mapper;

    private final HeatOverallRowMapper rowMapper;

    @java.lang.Override
    public List<HeatOverall> findAll() {
        return jdbc.query("SELECT * FROM heat_overall;", rowMapper);
    }

    @java.lang.Override
    public Optional<HeatOverall> findByName(String name) {
        HeatOverall HeatOverall = jdbc.queryForObject("SELECT * FROM heat_overall WHERE name = ?;",
                new Object[]{name},
                rowMapper);
        return Optional.ofNullable(HeatOverall);    }

    @java.lang.Override
    @SneakyThrows
    public void save(HeatOverall HeatOverall) {
        jdbc.update("INSERT INTO heat_overall (name, geo_json, value) VALUES (?, ?::jsonb, ?);",
                HeatOverall.getName(),
                mapper.writeValueAsString(HeatOverall.getGeoJson()),
                HeatOverall.getValue());
    }

    @java.lang.Override
    public void deleteAll() {
        jdbc.execute("DELETE FROM heat_overall;");
    }
}
