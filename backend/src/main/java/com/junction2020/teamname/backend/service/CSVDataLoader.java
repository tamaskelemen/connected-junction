package com.junction2020.teamname.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction2020.teamname.backend.model.RegionGrowthRate;
import com.junction2020.teamname.backend.repository.RegionGrowthRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.geojson.GeoJsonObject;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class CSVDataLoader {

    private final RegionGrowthRateRepository repository;

    private final ObjectMapper mapper;

    @SneakyThrows
    public void loadRegionsWithGrowthRate() {
        repository.deleteAll();
        File file = ResourceUtils.getFile("classpath:data/regions/regions_with_growth.csv");
        Path path = file.toPath();
        Files.readAllLines(path, StandardCharsets.ISO_8859_1)
                .stream()
                .map(line -> line.split(";"))
                .map(this::csvToRegionGrowthRate)
                .forEach(repository::save);
    }

    @SneakyThrows
    private RegionGrowthRate csvToRegionGrowthRate(String[] data) {
        return RegionGrowthRate.builder()
                .name(data[0])
                .geoJson(mapper.readValue(data[1], GeoJsonObject.class))
                .growthRate(Double.valueOf(data[2]))
                .build();
    }
}
