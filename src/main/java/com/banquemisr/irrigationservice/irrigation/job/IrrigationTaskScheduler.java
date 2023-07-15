package com.banquemisr.irrigationservice.irrigation.job;

import com.banquemisr.irrigationservice.irrigation.service.IrrigationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IrrigationTaskScheduler {

    private final IrrigationService irrigationService;

    @Scheduled(initialDelayString = "${irrigation.scheduler.initial-delay}",
            fixedRateString = "${irrigation.scheduler.fixed-rate}")
    public void startIrrigation() {
        log.info("scheduler [startIrrigation] started");

        Job job = new Job();
        irrigationService.startIrrigation(job);

        log.info("scheduler [startIrrigation] ended");
    }

    @Scheduled(initialDelayString = "${irrigation.scheduler.initial-delay}",
            fixedRateString = "${irrigation.scheduler.fixed-rate}")
    public void resetFinishedIrrigationSlots() {
        log.info("scheduler [updateSlotIrrigationStatus] started");
        irrigationService.resetFinishedIrrigationSlots();
        log.info("scheduler [updateSlotIrrigationStatus] ended");
    }
}
