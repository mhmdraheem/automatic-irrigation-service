package com.banquemisr.irrigationservice.plot.entity.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlotIrrigationTask {

    private Long slotId;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer amountLiters;
    private String plotCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlotIrrigationTask task = (PlotIrrigationTask) o;
        return Objects.equals(slotId, task.slotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slotId);
    }

    @Override
    public String toString() {
        return "IrrigationTask{" +
                "slotId=" + slotId +
                ", startTime=" + startTime +
                ", plotCode='" + plotCode + '\'' +
                '}';
    }
}
