package com.banquemisr.irrigationservice.plot.controller;

import com.banquemisr.irrigationservice.plot.dto.*;
import com.banquemisr.irrigationservice.plot.entity.projection.IrrigationPredictionResponse;
import com.banquemisr.irrigationservice.plot.service.PlotService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/plot")
public class PlotController {

    private final PlotService plotService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createPlot(@Valid @RequestBody CreatePlotRequest createPlotRequest) {
        plotService.createPlot(createPlotRequest);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePlot(@Valid @RequestBody UpdatePlotRequest updatePlotRequest) {
        plotService.updatePlot(updatePlotRequest);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<PlotDetailsResponse> listPlots(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize) {
       return plotService.listPlots(pageNum, pageSize);
    }

    @PutMapping(path = "slots/configure", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void configurePlotIrrigationSlots(@Valid @RequestBody ConfigureIrrigationRequest configureIrrigationRequest) {
        plotService.configurePlotIrrigationSlots(configureIrrigationRequest);
    }

    @GetMapping(path = "/predict", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public IrrigationPredictionResponse predictIrrigation(
            @Valid @RequestBody IrrigationPredictionRequest IrrigationPredictionRequest) {
        return plotService.predictIrrigation(IrrigationPredictionRequest);
    }
}
