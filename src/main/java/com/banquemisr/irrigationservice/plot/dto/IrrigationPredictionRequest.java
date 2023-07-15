package com.banquemisr.irrigationservice.plot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IrrigationPredictionRequest {

    @NotBlank
    private String area;

    @NotBlank
    private String cropType;
}
