package com.junction.rootkicskacsa.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction.rootkicskacsa.backend.model.RegionGrowthRate;
import com.junction.rootkicskacsa.backend.model.WaterLastYear;
import com.junction.rootkicskacsa.backend.model.WaterOverall;
import com.junction.rootkicskacsa.backend.repository.RegionGrowthRateRepository;
import com.junction.rootkicskacsa.backend.repository.WaterLastYearRepository;
import com.junction.rootkicskacsa.backend.repository.WaterOverallRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.geojson.GeoJsonObject;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class CSVDataLoaderService {

    private final RegionGrowthRateRepository repository;
    private final WaterOverallRepository waterOverallRepository;
    private final WaterLastYearRepository waterLastYearRepository;

    private final ObjectMapper mapper;

    @Transactional
    @SneakyThrows
    public void loadRegionsWithGrowthRate() {
        repository.deleteAll();
        File file = ResourceUtils.getFile("classpath:data/regions/regions_with_growth.csv");
        Path path = file.toPath();
        Files.readAllLines(path)
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

    @Transactional
    @SneakyThrows
    public void loadWaterOverall() {
        waterOverallRepository.deleteAll();
        File file = ResourceUtils.getFile("classpath:data/regions/water_overall.csv");
        Path path = file.toPath();
        Files.readAllLines(path)
                .stream()
                .map(line -> line.split(";"))
                .map(this::csvToWaterOverall)
                .forEach(waterOverallRepository::save);
    }

    @SneakyThrows
    private WaterOverall csvToWaterOverall(String[] data) {
        return WaterOverall.builder()
                .name(data[0])
                .geoJson(mapper.readValue(data[1], GeoJsonObject.class))
                .value(Double.valueOf(data[2]))
                .build();
    }

    @Transactional
    @SneakyThrows
    public void loadWaterLastYear() {
        waterLastYearRepository.deleteAll();
        File file = ResourceUtils.getFile("classpath:data/regions/water_lastyear.csv");
        Path path = file.toPath();
        Files.readAllLines(path)
                .stream()
                .map(line -> line.split(";"))
                .map(this::csvToWaterLastYear)
                .forEach(waterLastYearRepository::save);
    }

    @SneakyThrows
    private WaterLastYear csvToWaterLastYear(String[] data) {
        return WaterLastYear.builder()
                .name(data[0])
                .geoJson(mapper.readValue(data[1], GeoJsonObject.class))
                .value(Double.valueOf(data[2]))
                .build();
    }
}
