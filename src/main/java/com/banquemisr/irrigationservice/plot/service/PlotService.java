package com.banquemisr.irrigationservice.plot.service;

import com.banquemisr.irrigationservice.plot.dto.CreatePlotRequest;
import com.banquemisr.irrigationservice.plot.dto.UpdatePlotRequest;
import com.banquemisr.irrigationservice.plot.entity.Plot;
import com.banquemisr.irrigationservice.plot.entity.PlotRepository;
import com.banquemisr.irrigationservice.plot.excpetion.PlotAlreadyExistsException;
import com.banquemisr.irrigationservice.plot.excpetion.PlotNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PlotService {

    private final PlotRepository plotRepository;

    @Transactional
    public void createPlot(CreatePlotRequest createPlotRequest) {
        validateRequest(createPlotRequest);
        Plot plot = createPlotEntity(createPlotRequest);
        savePlot(plot);
    }

    private void validateRequest(CreatePlotRequest createPlotRequest) {
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
}
