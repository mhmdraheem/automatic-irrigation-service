package com.banquemisr.irrigationservice.plot.service;

import com.banquemisr.irrigationservice.plot.dto.ConfigureIrrigationRequest;
import com.banquemisr.irrigationservice.plot.excpetion.InvalidSlotTimingException;
import com.banquemisr.irrigationservice.plot.excpetion.OverlappingSlotsException;
import com.banquemisr.irrigationservice.plot.repository.PlotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;

import static com.banquemisr.irrigationservice.plot.dto.ConfigureIrrigationRequest.IrrigationSlot;

@ExtendWith(MockitoExtension.class)
public class PlotServiceUnitTest {

    @Mock
    PlotRepository plotRepository;

    @InjectMocks
    PlotService plotService;

    ConfigureIrrigationRequest request;

    @BeforeEach
    public void init() {
        request = new ConfigureIrrigationRequest();
        request.setPlotCode("PLOT-123");
    }

    @Test
    void givenInvalidSlotTiming_whenConfigurePlotSlots_thenThrowInvalidSlotTimingException() {
        IrrigationSlot slot = new IrrigationSlot();
        slot.setStartTime(LocalTime.parse("12:00:00"));
        slot.setEndTime(LocalTime.parse("08:00:00"));

        request.setIrrigationSlots(List.of(slot));

        Assertions.assertThrows(InvalidSlotTimingException.class, () -> {
            plotService.configurePlotIrrigationSlots(request);
        });
    }

    @Test
    void givenOverlappedSlots_whenConfigurePlotSlots_thenThrowOverlappingSlotsException() {

        // case #1
        IrrigationSlot slot1 = new IrrigationSlot();
        slot1.setStartTime(LocalTime.parse("09:00:00"));
        slot1.setEndTime(LocalTime.parse("11:00:00"));

        IrrigationSlot slot2 = new IrrigationSlot();
        slot2.setStartTime(LocalTime.parse("10:00:00"));
        slot2.setEndTime(LocalTime.parse("15:00:00"));

        request.setIrrigationSlots(List.of(slot1, slot2));

        Assertions.assertThrows(OverlappingSlotsException.class, () -> {
            plotService.configurePlotIrrigationSlots(request);
        });

        // case #2
        slot1 = new IrrigationSlot();
        slot1.setStartTime(LocalTime.parse("09:00:00"));
        slot1.setEndTime(LocalTime.parse("11:00:00"));

        slot2 = new IrrigationSlot();
        slot2.setStartTime(LocalTime.parse("05:00:00"));
        slot2.setEndTime(LocalTime.parse("10:00:00"));

        request.setIrrigationSlots(List.of(slot1, slot2));

        Assertions.assertThrows(OverlappingSlotsException.class, () -> {
            plotService.configurePlotIrrigationSlots(request);
        });

        // case #3
        slot1 = new IrrigationSlot();
        slot1.setStartTime(LocalTime.parse("09:00:00"));
        slot1.setEndTime(LocalTime.parse("11:00:00"));

        slot2 = new IrrigationSlot();
        slot2.setStartTime(LocalTime.parse("10:00:00"));
        slot2.setEndTime(LocalTime.parse("10:30:00"));

        request.setIrrigationSlots(List.of(slot1, slot2));

        Assertions.assertThrows(OverlappingSlotsException.class, () -> {
            plotService.configurePlotIrrigationSlots(request);
        });
    }
}
