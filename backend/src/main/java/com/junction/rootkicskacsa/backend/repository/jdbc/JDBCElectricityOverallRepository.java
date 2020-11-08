package com.junction.rootkicskacsa.backend.repository.jdbc;

import com.junction.rootkicskacsa.backend.model.ElectricityOverall;
import com.junction.rootkicskacsa.backend.repository.ElectricityOverallRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction.rootkicskacsa.backend.repository.jdbc.mapper.ElectricityOverallRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JDBCElectricityOverallRepository implements ElectricityOverallRepository {
    private final JdbcTemplate jdbc;

    private final ObjectMapper mapper;

    private final ElectricityOverallRowMapper rowMapper;

    @java.lang.Override
    public List<ElectricityOverall> findAll() {
        return jdbc.query("SELECT * FROM electricity_overall;", rowMapper);
    }

    @java.lang.Override
    public Optional<ElectricityOverall> findByName(String name) {
        ElectricityOverall electricityOverall = jdbc.queryForObject("SELECT * FROM electricity_overall WHERE name = ?;",
                new Object[]{name},
                rowMapper);
        return Optional.ofNullable(electricityOverall);    }

    @java.lang.Override
    @SneakyThrows
    public void save(ElectricityOverall electricityOverall) {
        jdbc.update("INSERT INTO electricity_overall (name, geo_json, value) VALUES (?, ?::jsonb, ?);",
                electricityOverall.getName(),
                mapper.writeValueAsString(electricityOverall.getGeoJson()),
                electricityOverall.getValue());
    }

    @java.lang.Override
    public void deleteAll() {
        jdbc.execute("DELETE FROM electricity_overall;");
    }
}
