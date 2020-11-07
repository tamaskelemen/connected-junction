package com.junction2020.teamname.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junction2020.teamname.backend.model.RegionGrowthRate;
import com.junction2020.teamname.backend.repository.RegionGrowthRateRepository;
import lombok.SneakyThrows;
import org.geojson.GeoJsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;

@Disabled
@SpringBootTest
public class GeoJsonTest {

    @Autowired
    private RegionGrowthRateRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @SneakyThrows
    public void testGeoJsonSerialisation() {
        var serialised = mapper.readValue(getGeoJson(), GeoJsonObject.class);
        var deserialised = mapper.writeValueAsString(serialised);
    }

    @Test
    @SneakyThrows
    public void testGeoJsonReadWrite() {

        var name = "unique-region-name";
        var obj = mapper.readValue(getGeoJson(), GeoJsonObject.class);

        var region = RegionGrowthRate.builder()
                .name(name)
                .geoJson(obj)
                .growthRate(0.76)
                .build();

        repository.save(region);

        var savedRegion = repository.findById(name).orElseThrow();

        Assertions.assertEquals(region, savedRegion);
    }

    @SneakyThrows
    private String getGeoJson() {
        return Files.readString(ResourceUtils.getFile("classpath:geo.json").toPath());
    }
}
