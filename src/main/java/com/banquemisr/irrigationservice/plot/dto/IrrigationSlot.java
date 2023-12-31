package com.banquemisr.irrigationservice.plot.dto;

import com.banquemisr.irrigationservice.plot.entity.PlotIrrigationSlot;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalTime;
import java.util.Objects;

@Data
public class IrrigationSlot {

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime startTime;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime endTime;

    @NotNull
    @Positive
    private Integer amountLiters;

    private PlotIrrigationSlot.Status status = PlotIrrigationSlot.Status.READY;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IrrigationSlot that = (IrrigationSlot) o;
        return Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }
}