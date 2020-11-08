package com.junction.rootkicskacsa.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction.rootkicskacsa.backend.repository.RegionGrowthRateRepository;
import com.junction.rootkicskacsa.backend.controller.ApiController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiTest {

    @Autowired
    private ApiController api;

    @Autowired
    private RegionGrowthRateRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void test() {
//        repository.findAll().iterator().
    }
}
