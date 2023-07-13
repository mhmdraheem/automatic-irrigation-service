package com.banquemisr.irrigationservice.plot.controller;

import com.banquemisr.irrigationservice.plot.dto.CreatePlotRequest;
import com.banquemisr.irrigationservice.plot.dto.UpdatePlotRequest;
import com.banquemisr.irrigationservice.plot.service.PlotService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
