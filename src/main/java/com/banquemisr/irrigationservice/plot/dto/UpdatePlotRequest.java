package com.banquemisr.irrigationservice.plot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePlotRequest {

    @NotBlank
    private String code;
    private String area;
    private String cropType;
}
