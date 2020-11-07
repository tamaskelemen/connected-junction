package com.junction.rootkicskacsa.backend.repository.jdbc.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction.rootkicskacsa.backend.model.ElectricityLastyear;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.geojson.GeoJsonObject;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
@RequiredArgsConstructor
public class ElectricityLastyearRowMapper implements RowMapper<ElectricityLastyear> {
    private final ObjectMapper mapper;

    @java.lang.Override
    @SneakyThrows
    public ElectricityLastyear mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
        var json = rs.getString("geo_json");

        return ElectricityLastyear.builder()
                .name(rs.getString("name"))
                .geoJson(mapper.readValue(json, GeoJsonObject.class))
                .value(rs.getDouble("value"))
                .build();
    }
}
