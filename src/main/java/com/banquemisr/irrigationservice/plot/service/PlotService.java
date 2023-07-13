package com.banquemisr.irrigationservice.plot.service;

import com.banquemisr.irrigationservice.plot.dto.*;
import com.banquemisr.irrigationservice.plot.entity.Plot;
import com.banquemisr.irrigationservice.plot.entity.PlotIrrigationSlot;
import com.banquemisr.irrigationservice.plot.excpetion.InvalidSlotTimingException;
import com.banquemisr.irrigationservice.plot.excpetion.OverlappingSlotsException;
import com.banquemisr.irrigationservice.plot.excpetion.PlotAlreadyExistsException;
import com.banquemisr.irrigationservice.plot.excpetion.PlotNotFoundException;
import com.banquemisr.irrigationservice.plot.repository.PlotRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlotService {

    private final PlotRepository plotRepository;

    @Transactional
    public void createPlot(CreatePlotRequest createPlotRequest) {
        validateCreatePlotRequest(createPlotRequest);
        Plot plot = createPlotEntity(createPlotRequest);
        savePlot(plot);
    }

    private void validateCreatePlotRequest(CreatePlotRequest createPlotRequest) {
        validatePlotNotExists(createPlotRequest.getCode());
    }

    private void validatePlotNotExists(String plotCode) {
        findPlotByCode(plotCode)
                .ifPresent(plot -> { throw new PlotAlreadyExistsException(plotCode); });
    }

    private Optional<Plot> findPlotByCode(String plotCode) {
        return plotRepository.findPlotByCode(plotCode);
    }

    private Plot createPlotEntity(CreatePlotRequest createPlotRequest) {
        Plot plot = new Plot();
        plot.setCode(createPlotRequest.getCode());
        plot.setArea(createPlotRequest.getArea());
        plot.setCropType(createPlotRequest.getCropType());
        return plot;
    }

    private void savePlot(Plot plot) {
        plotRepository.save(plot);
    }

    @Transactional
    public void updatePlot(UpdatePlotRequest updatePlotRequest) {
        Plot plot = findPlot(updatePlotRequest.getCode());
        updatePlot(plot, updatePlotRequest);
    }

    private Plot findPlot(String plotCode) {
        Optional<Plot> plot = findPlotByCode(plotCode);
        if(plot.isEmpty()) {
            throw new PlotNotFoundException(plotCode);
        } else {
            return plot.get();
        }
    }

    private void updatePlot(Plot plot, UpdatePlotRequest updatePlotRequest) {
        plot.setArea(updatePlotRequest.getArea());
        plot.setCropType(updatePlotRequest.getCropType());
    }

    @Transactional
    public void configurePlotIrrigationSlots(ConfigureIrrigationRequest irrigationRequest) {
        validateIrrigationRequest(irrigationRequest);

        Plot plot = findPlot(irrigationRequest.getPlotCode());
        List<PlotIrrigationSlot> plotIrrigationSlots = createSlotEntities(irrigationRequest);
        configurePlot(plot, plotIrrigationSlots);
    }

    private void validateIrrigationRequest(ConfigureIrrigationRequest irrigationRequest) {
        validateSlotTiming(irrigationRequest.getIrrigationSlots());
        validateNotOverlappingSlots(irrigationRequest.getIrrigationSlots());
    }

    private void validateSlotTiming(List<IrrigationSlot> slots) {
        for(IrrigationSlot irrigationSlot : slots) {
            LocalTime startTime = irrigationSlot.getStartTime();
            LocalTime endTime = irrigationSlot.getEndTime();
            if (startTime.equals(endTime) || startTime.isAfter(endTime)) {
                throw new InvalidSlotTimingException();
            }
        }
    }

    private void validateNotOverlappingSlots(List<IrrigationSlot> slots) {
        for(int i = 0; i< slots.size(); i++) {
            IrrigationSlot s1 = slots.get(i);
            for(int j = 0; j < slots.size(); j++) {
                if(i == j) continue; // prevent comparing same slot
                IrrigationSlot s2 = slots.get(j);
                if(s1.getStartTime().isBefore(s2.getEndTime()) && s2.getStartTime().isBefore(s1.getEndTime())) {
                    throw new OverlappingSlotsException();
                }
            }
        }
    }

    private List<PlotIrrigationSlot> createSlotEntities(ConfigureIrrigationRequest irrigationRequest) {
        return irrigationRequest.getIrrigationSlots().stream()
                .map(this::createIrrigationEntity)
                .collect(Collectors.toList());
    }

    private PlotIrrigationSlot createIrrigationEntity(IrrigationSlot irrigationSlotDto) {
        PlotIrrigationSlot plotIrrigationSlot = new PlotIrrigationSlot();
        plotIrrigationSlot.setStartTime(irrigationSlotDto.getStartTime());
        plotIrrigationSlot.setEndTime(irrigationSlotDto.getEndTime());
        plotIrrigationSlot.setAmountLiters(irrigationSlotDto.getAmountLiters());
        return plotIrrigationSlot;
    }

    private void configurePlot(Plot plot, List<PlotIrrigationSlot> plotIrrigationSlots) {
        plot.configureIrrigationSlots(plotIrrigationSlots);
    }

    @Transactional
    public Page<PlotDetailsResponse> listPlots(int pageNum, int pageSize) {
        Page<Plot> plotsPage = getPlotsPage(pageNum, pageSize);
        return mapToPlotDetailsResponsePage(plotsPage);
    }

    private Page<Plot> getPlotsPage(int pageNum, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        return plotRepository.findPlotWithSlots(pageRequest);
    }

    private Page<PlotDetailsResponse> mapToPlotDetailsResponsePage(Page<Plot> plotsPage) {
        return plotsPage.map(plot -> {
            PlotDetailsResponse response = new PlotDetailsResponse();
            response.setArea(plot.getArea());
            response.setCode(plot.getCode());
            response.setCropType(plot.getCropType());
            response.setIrrigationSlots(plot.getIrrigationSlots().stream()
                    .map(this::createIrrigationSlotDto)
                    .collect(Collectors.toList()));
            return response;
        });
    }

    private IrrigationSlot createIrrigationSlotDto(PlotIrrigationSlot slotEntity) {
        IrrigationSlot irrigationSlot = new IrrigationSlot();
        irrigationSlot.setStartTime(slotEntity.getStartTime());
        irrigationSlot.setEndTime(slotEntity.getEndTime());
        irrigationSlot.setAmountLiters(slotEntity.getAmountLiters());
        return irrigationSlot;
    }
}
