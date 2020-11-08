package com.junction.rootkicskacsa.backend.controller;

import com.junction.rootkicskacsa.backend.model.*;
import com.junction.rootkicskacsa.backend.repository.EstateRepository;
import com.junction.rootkicskacsa.backend.repository.RegionGrowthRateRepository;
import com.junction.rootkicskacsa.backend.repository.WaterOverallRepository;
import com.junction.rootkicskacsa.backend.service.CSVDataLoaderService;
import com.junction.rootkicskacsa.backend.service.EstatePredictionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final CSVDataLoaderService csv;
    private final EstatePredictionService estatePredictionService;

    private final RegionGrowthRateRepository regionGrowthRateRepository;
    private final EstateRepository estateRepository;
    private final WaterOverallRepository waterOverallRepository;

    @GetMapping("/loadRegions")
    public void loadRegions() {
        try {
            csv.loadRegionsWithGrowthRate();
        } catch (Exception ex) {
            log.error("Failed to load region data", ex);
            throw new ApiException();
        }
    }

    @GetMapping("/loadWaterOverall")
    public void loadWaterOverall() {
        try {
            csv.loadWaterOverall();
        } catch (Exception ex) {
            log.error("Failed to load region data", ex);
            throw new ApiException();
        }
    }

    @GetMapping("/loadWaterLastYear")
    public void loadWaterLastYear() {
        try {
            csv.loadWaterLastYear();
        } catch (Exception ex) {
            log.error("Failed to load region data", ex);
            throw new ApiException();
        }
    }

    @GetMapping("/loadPredictions")
    public void loadPredictions() {
        try {
            csv.loadElectricityPredictionsFromCSV();
            csv.loadHeatPredictionsFromCSV();
            csv.loadWaterPredictionsFromCSV();
        } catch (Exception ex) {
            log.error("Failed to load prediction data from csv", ex);
            throw new ApiException();
        }
    }
    @GetMapping("/loadElectricityLastyear")
    public void loadElectricityLastyear() {
        try {
            csv.loadElectricityLastyear();
        } catch (Exception ex) {
            log.error("Failed to load region data", ex);
            throw new ApiException();
        }
    }

    @GetMapping("/loadElectricityOverall")
    public void loadElectricityOverall() {
        try {
            csv.loadElectricityOverall();
        } catch (Exception ex) {
            log.error("Failed to load region data", ex);
            throw new ApiException();
        }
    }

    @GetMapping("/loadHeatOverall")
    public void loadHeatOverall() {
        try {
            csv.loadHeatOverall();
        } catch (Exception ex) {
            log.error("Failed to load heat data", ex);
            throw new ApiException();
        }
    }

    @GetMapping(path = {"/getAll", "/region"}, produces = "application/json")
    public List<RegionGrowthRate> getAllRegionGrowthRateData() {
        try {
            return regionGrowthRateRepository.findAll();
        } catch (Exception ex) {
            log.error("Failed to query region data", ex);
            throw new ApiException();
        }
    }

    @GetMapping(path = {"/getByName", "regionByName"}, produces = "application/json")
    public RegionGrowthRate getByName(@RequestParam String name) {
        try {
            return regionGrowthRateRepository.findByName(name).get();
        } catch (Exception ex) {
            log.error("Failed to query region data by region name", ex);
            throw new ApiException();
        }
    }

    @GetMapping(path = "/estates", produces = "application/json")
    public List<Estate> getEstates() {
        try {
            return estateRepository.findAll();
        } catch (Exception ex) {
            log.error("Failed to query estate data", ex);
            throw new ApiException();
        }
    }

    @GetMapping(path = "/estatesSimplified", produces = "application/json")
    public List<EstateSimplified> getEstatesSimplified() {
        try {
            return estateRepository.findAllRelevant();
        } catch (Exception ex) {
            log.error("Failed to query simplified estate data", ex);
            throw new ApiException();
        }
    }

    @GetMapping(path = "/waterOverall", produces = "application/json")
    public List<WaterOverall> getWaterOverallData() {
        try {
            return waterOverallRepository.findAll();
        } catch (Exception ex) {
            log.error("Failed to query simplified estate data", ex);
            throw new ApiException();
        }
    }

    @GetMapping(path = "/estatePredictions", produces = "application/json")
    public EstatePredictions getEstatePredictions(@RequestParam Long objectId) {
        return estatePredictionService.getEstatePredictions(objectId);
    }
}
