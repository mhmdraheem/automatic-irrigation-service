package com.banquemisr.irrigationservice.sensor.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/sensor")
public class SensorController {

    private final List<String> errorPlotCodes = List.of("PLOT-ALEX-218", "PLOT-ASS-1584");
    //private final List<String> errorPlotCodes = List.of();

    @PostMapping(path = "/irrigate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> irrigatePlot(@RequestBody Map<String, Object> body) throws InterruptedException {
        log.info("sensor API called: {}", body);

        String plotCode = (String) body.get("plotCode");
        if(!errorPlotCodes.contains(plotCode)) {
            Thread.sleep(3000);
            return ResponseEntity.internalServerError().build();
        } else {
            Thread.sleep(2000);
            return ResponseEntity.ok().build();
        }
    }
}
