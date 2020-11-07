package com.junction2020.teamname.backend.repository.jdbc.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction2020.teamname.backend.model.Estate;
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
public class EstateRowMapper implements RowMapper<Estate> {

    @Override
    public Estate mapRow(ResultSet rs, int rowNum) throws SQLException {
        var json = rs.getString("data");

        return Estate.builder().id(rs.getLong("id")).data(json).build();
    }
}
