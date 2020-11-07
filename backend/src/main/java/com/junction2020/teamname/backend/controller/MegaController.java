package com.junction2020.teamname.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction2020.teamname.backend.model.Estate;
import com.junction2020.teamname.backend.model.RegionGrowthRate;
import com.junction2020.teamname.backend.repository.EstateRepository;
import com.junction2020.teamname.backend.repository.RegionGrowthRateRepository;
import com.junction2020.teamname.backend.model.Estate;
import com.junction2020.teamname.backend.repository.EstateRepository;
import com.junction2020.teamname.backend.service.CSVDataLoaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MegaController {

    private final CSVDataLoaderService csv;

    private final RegionGrowthRateRepository repository;
    private final EstateRepository estateRepository;

    private final ObjectMapper mapper;

    @GetMapping("/loadRegions")
    public void loadRegions() {
        try {
            csv.loadRegionsWithGrowthRate();
        } catch (Exception ex) {
            log.error("Failed to load region data", ex);
            throw new ApiException();
        }
    }

    @GetMapping("/getAll")
    public List<RegionGrowthRate> getAll() {
        try {
            return repository.findAll();
        } catch (Exception ex) {
            log.error("Failed to query region data", ex);
            throw new ApiException();
        }
    }

    @GetMapping("/getByName")
    public RegionGrowthRate getByName(@RequestParam String name) {
        try {
            return repository.findByName(name).get();
        } catch (Exception ex) {
            log.error("Failed to query region data by region name", ex);
            throw new ApiException();
        }
    }

    @GetMapping("/estates")
    public List<Estate> estates()
    {
        try {
            return estateRepository.findAll();
        } catch (Exception ex) {
            log.error("Failed to query region data by region name", ex);
            throw new ApiException();
        }
    }
}
