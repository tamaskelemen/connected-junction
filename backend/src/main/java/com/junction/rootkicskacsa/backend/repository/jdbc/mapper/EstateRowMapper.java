package com.junction.rootkicskacsa.backend.repository.jdbc.mapper;

import com.junction.rootkicskacsa.backend.model.Estate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EstateRowMapper implements RowMapper<Estate> {

    @Override
    public Estate mapRow(ResultSet rs, int rowNum) throws SQLException {
        var json = rs.getString("data");
        return Estate.builder()
          .id(rs.getLong("id"))
          .data(json)
          .build();
    }
}
