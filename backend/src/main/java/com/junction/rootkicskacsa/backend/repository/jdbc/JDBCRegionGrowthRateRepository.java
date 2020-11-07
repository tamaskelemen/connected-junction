package com.junction.rootkicskacsa.backend.repository.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction.rootkicskacsa.backend.model.RegionGrowthRate;
import com.junction.rootkicskacsa.backend.repository.RegionGrowthRateRepository;
import com.junction.rootkicskacsa.backend.repository.jdbc.mapper.RegionGrowthRateRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JDBCRegionGrowthRateRepository implements RegionGrowthRateRepository {
    
    private final JdbcTemplate jdbc;

    private final ObjectMapper mapper;

    private final RegionGrowthRateRowMapper rowMapper;


    @Override
    public List<RegionGrowthRate> findAll() {
        return jdbc.query("SELECT * FROM region_growth_rate;", rowMapper);
    }

    @Override
    public Optional<RegionGrowthRate> findByName(String name) {
        RegionGrowthRate regionGrowthRate = jdbc.queryForObject("SELECT * FROM region_growth_rate WHERE name = ?;",
                new Object[]{name},
                rowMapper);
        return Optional.ofNullable(regionGrowthRate);
    }

    @Override
    @SneakyThrows
    public void save(RegionGrowthRate region) {
        jdbc.update("INSERT INTO region_growth_rate (name, geo_json, growth_rate) VALUES (?, ?::jsonb, ?);",
                region.getName(),
                mapper.writeValueAsString(region.getGeoJson()),
                region.getGrowthRate());

    }

    @Override
    public void deleteAll() {
        jdbc.execute("DELETE FROM region_growth_rate;");
    }
}
