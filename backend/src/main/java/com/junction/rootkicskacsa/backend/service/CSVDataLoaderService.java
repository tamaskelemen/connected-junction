package com.junction.rootkicskacsa.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction.rootkicskacsa.backend.model.*;
import com.junction.rootkicskacsa.backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.geojson.GeoJsonObject;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CSVDataLoaderService {

    private final RegionGrowthRateRepository repository;
    private final WaterOverallRepository waterOverallRepository;
    private final WaterLastYearRepository waterLastYearRepository;
    private final ElectricityLastyearRepository electricityLastyearRepository;
    private final ElectricityOverallRepository electricityOverallRepository;
    private final HeatOverallRepository heatOverallRepository;
    private final HeatLastYearRepository heatLastYearRepository;

    private final PredictedElectricityRepository predictedElectricityRepository;
    private final PredictedHeatRepository predictedHeatRepository;
    private final PredictedWaterRepository predictedWaterRepository;

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

    @Transactional
    @SneakyThrows
    public void loadElectricityPredictionsFromCSV() {
        List<PredictedElectricity> records = Files.readAllLines(ResourceUtils.getFile("classpath:data/predicted_electricity.csv").toPath())
          .stream()
          .map(line -> line.split(";"))
          .map(data -> PredictedElectricity.builder()
            .estateObjectId(Long.parseLong(data[0]))
            .value(Double.parseDouble(data[1]))
            .build())
          .collect(Collectors.toList());
        predictedElectricityRepository.deleteAll();
        predictedElectricityRepository.saveAll(records);
        log.info("Electricity predictions loaded");
    }

    @Transactional
    @SneakyThrows
    public void loadHeatPredictionsFromCSV() {
        List<PredictedHeat> records = Files.readAllLines(ResourceUtils.getFile("classpath:data/predicted_heat.csv").toPath())
          .stream()
          .map(line -> line.split(";"))
          .map(data -> PredictedHeat.builder()
            .estateObjectId(Long.parseLong(data[0]))
            .value(Double.parseDouble(data[1]))
            .build())
          .collect(Collectors.toList());
        predictedHeatRepository.deleteAll();
        predictedHeatRepository.saveAll(records);
        log.info("Heat predictions loaded");
    }

    @Transactional
    @SneakyThrows
    public void loadWaterPredictionsFromCSV() {
        List<PredictedWater> records = Files.readAllLines(ResourceUtils.getFile("classpath:data/predicted_water.csv").toPath())
          .stream()
          .map(line -> line.split(";"))
          .map(data -> PredictedWater.builder()
            .estateObjectId(Long.parseLong(data[0]))
            .value(Double.parseDouble(data[1]))
            .build())
          .collect(Collectors.toList());
        predictedWaterRepository.deleteAll();
        predictedWaterRepository.saveAll(records);
        log.info("Water predictions loaded");
    }

    @Transactional
    @SneakyThrows
    public void loadElectricityLastyear() {
        electricityLastyearRepository.deleteAll();
        File file = ResourceUtils.getFile("classpath:data/regions/electricity_lastyear.csv");
        Path path = file.toPath();
        Files.readAllLines(path)
                .stream()
                .map(line -> line.split(";"))
                .map(this::csvToElectricityLastyear)
                .forEach(electricityLastyearRepository::save);
    }

    @SneakyThrows
    private ElectricityLastyear csvToElectricityLastyear(String[] data) {
        return ElectricityLastyear.builder()
                .name(data[0])
                .geoJson(mapper.readValue(data[1], GeoJsonObject.class))
                .value(Double.valueOf(data[2]))
                .build();
    }

    @Transactional
    @SneakyThrows
    public void loadElectricityOverall() {
        electricityOverallRepository.deleteAll();
        File file = ResourceUtils.getFile("classpath:data/regions/electricity_overall.csv");
        Path path = file.toPath();
        Files.readAllLines(path)
                .stream()
                .map(line -> line.split(";"))
                .map(this::csvToElectricityOverall)
                .forEach(electricityOverallRepository::save);
    }

    @SneakyThrows
    private ElectricityOverall csvToElectricityOverall(String[] data) {
        return ElectricityOverall.builder()
                .name(data[0])
                .geoJson(mapper.readValue(data[1], GeoJsonObject.class))
                .value(Double.valueOf(data[2]))
                .build();
    }

    @Transactional
    @SneakyThrows
    public void loadHeatOverall() {
        heatOverallRepository.deleteAll();
        File file = ResourceUtils.getFile("classpath:data/regions/heat_overall.csv");
        Path path = file.toPath();
        Files.readAllLines(path)
                .stream()
                .map(line -> line.split(";"))
                .map(this::csvToHeatOverall)
                .forEach(heatOverallRepository::save);
    }

    @SneakyThrows
    private HeatOverall csvToHeatOverall(String[] data) {
        return HeatOverall.builder()
                .name(data[0])
                .geoJson(mapper.readValue(data[1], GeoJsonObject.class))
                .value(Double.valueOf(data[2]))
                .build();
    }

    @Transactional
    @SneakyThrows
    public void loadHeatLastYear() {
        heatLastYearRepository.deleteAll();
        File file = ResourceUtils.getFile("classpath:data/regions/heat_lastyear.csv");
        Path path = file.toPath();
        Files.readAllLines(path)
                .stream()
                .map(line -> line.split(";"))
                .map(this::csvToHeatLastYear)
                .forEach(heatLastYearRepository::save);
    }

    @SneakyThrows
    private HeatLastYear csvToHeatLastYear(String[] data) {
        return HeatLastYear.builder()
                .name(data[0])
                .geoJson(mapper.readValue(data[1], GeoJsonObject.class))
                .value(Double.valueOf(data[2]))
                .build();
    }
}
