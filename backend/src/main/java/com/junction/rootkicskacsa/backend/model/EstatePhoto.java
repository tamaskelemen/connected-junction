package com.junction.rootkicskacsa.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@NoArgsConstructor
@Jacksonized
@SuperBuilder
public class EstatePhoto {

    public static String PAA = "PAA";

    @JsonProperty("@url")
    private String url;

    @JsonProperty("@type")
    private String type;

}
