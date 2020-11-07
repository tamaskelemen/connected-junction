package com.junction2020.teamname.backend.repository.jdbc.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction2020.teamname.backend.model.RegionGrowthRate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.geojson.GeoJsonObject;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class RegionGrowthRateRowMapper implements RowMapper<RegionGrowthRate> {

    private final ObjectMapper mapper;

    @Override
    @SneakyThrows
    public RegionGrowthRate mapRow(ResultSet rs, int rowNum) throws SQLException {

        var json = rs.getString("geo_json");

        return RegionGrowthRate.builder()
                .name(rs.getString("name"))
                .geoJson(mapper.readValue(json, GeoJsonObject.class))
                .growthRate(rs.getDouble("growth_rate"))
                .build();
    }
}
