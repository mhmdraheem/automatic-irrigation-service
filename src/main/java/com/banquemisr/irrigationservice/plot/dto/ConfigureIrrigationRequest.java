package com.banquemisr.irrigationservice.plot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Data
public class ConfigureIrrigationRequest {

    @NotBlank
    private String plotCode;

    @Valid
    @NotNull
    private List<IrrigationSlot> irrigationSlots;

    @Data
    public static class IrrigationSlot {

        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        private LocalTime startTime;

        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        private LocalTime endTime;

        @NotNull
        @Positive
        private Integer amountLiters;

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
}
