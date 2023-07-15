package com.banquemisr.irrigationservice.irrigation.job;

import com.banquemisr.irrigationservice.plot.entity.projection.PlotIrrigationTask;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class Job {

    private Info info = new Info();
    private List<PlotIrrigationTask> tasks;

    @Data
    public static class Info {
        private String number = UUID.randomUUID().toString();
        private LocalDateTime startTime = LocalDateTime.now();
    }
}
