package com.junction.rootkicskacsa.backend.repository.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction.rootkicskacsa.backend.model.ElectricityLastyear;
import com.junction.rootkicskacsa.backend.repository.ElectricityLastyearRepository;
import com.junction.rootkicskacsa.backend.repository.jdbc.mapper.ElectricityLastyearRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JDBCElectricityLastyearRepository implements ElectricityLastyearRepository {
    private final JdbcTemplate jdbc;

    private final ObjectMapper mapper;

    private final ElectricityLastyearRowMapper rowMapper;

    @java.lang.Override
    public List<ElectricityLastyear> findAll() {
        return jdbc.query("SELECT * FROM electricity_lastyear;", rowMapper);
    }

    @java.lang.Override
    public Optional<ElectricityLastyear> findByName(String name) {
        ElectricityLastyear electricityLastyear = jdbc.queryForObject("SELECT * FROM electricity_lastyear WHERE name = ?;",
                new Object[]{name},
                rowMapper);
        return Optional.ofNullable(electricityLastyear);    }

    @java.lang.Override
    @SneakyThrows
    public void save(ElectricityLastyear electricityLastyear) {
        jdbc.update("INSERT INTO electricity_lastyear (name, geo_json, value) VALUES (?, ?::jsonb, ?);",
                electricityLastyear.getName(),
                mapper.writeValueAsString(electricityLastyear.getGeoJson()),
                electricityLastyear.getValue());
    }

    @java.lang.Override
    public void deleteAll() {
        jdbc.execute("DELETE FROM electricity_lastyear;");
    }
}
