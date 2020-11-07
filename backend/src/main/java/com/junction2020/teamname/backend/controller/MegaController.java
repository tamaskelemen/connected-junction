package com.junction2020.teamname.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction2020.teamname.backend.model.RegionGrowthRate;
import com.junction2020.teamname.backend.repository.RegionGrowthRateRepository;
import com.junction2020.teamname.backend.service.CSVDataLoaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MegaController {

    private final CSVDataLoaderService csv;

    private final RegionGrowthRateRepository repository;

    private final ObjectMapper mapper;

    @GetMapping("/loadRegions")
    public void loadRegions() {
        csv.loadRegionsWithGrowthRate();
    }

    @GetMapping("/getAll")
    public List<RegionGrowthRate> getAll() {
        return repository.findAll();
    }

    @GetMapping("/getByName")
    public RegionGrowthRate getByName(@RequestParam String name) {
        return repository.findByName(name).get();
    }

    private <T> Stream<T> toStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
