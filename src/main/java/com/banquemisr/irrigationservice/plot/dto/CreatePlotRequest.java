package com.banquemisr.irrigationservice.plot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Objects;

@Data
public class CreatePlotRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String area;

    @NotBlank
    private String cropType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatePlotRequest plot = (CreatePlotRequest) o;
        return Objects.equals(code, plot.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
