package com.junction2020.teamname.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction2020.teamname.backend.controller.MegaController;
import com.junction2020.teamname.backend.repository.RegionGrowthRateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiTest {

    @Autowired
    private MegaController api;

    @Autowired
    private RegionGrowthRateRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void test() {
//        repository.findAll().iterator().
    }
}
