package com.banquemisr.irrigationservice.plot.entity.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IrrigationPredictionResponse {

    private BigDecimal avgLiters;
    private LocalTime minSlotTime;
    private LocalTime maxSlotTime;
    private LocalTime avgSlotTime;
}
