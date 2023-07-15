package com.banquemisr.irrigationservice.plot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePlotRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String area;

    @NotBlank
    private String cropType;
}
