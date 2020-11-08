package com.junction.rootkicskacsa.backend.repository.jdbc.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction.rootkicskacsa.backend.model.EstatePhoto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class EstatePhotoRowMapper implements RowMapper<EstatePhoto> {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public EstatePhoto mapRow(ResultSet rs, int rowNum) {
        String json = rs.getObject("pictures", String.class);
        EstatePhoto[] array = objectMapper.readValue(json, EstatePhoto[].class);
        return Arrays.stream(array)
          .filter(photo -> photo.getType().equals(EstatePhoto.PAA))
          .findFirst()
          .orElse(null);
    }
}