package com.banquemisr.irrigationservice.plot.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ConfigureIrrigationRequest {

    @NotBlank
    private String plotCode;

    @Valid
    @NotNull
    private List<IrrigationSlot> irrigationSlots;
}
