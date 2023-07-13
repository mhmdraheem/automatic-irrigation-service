package com.banquemisr.irrigationservice.plot.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlotDetailsResponse {
    private String code;
    private String area;
    private String cropType;
    private List<IrrigationSlot> irrigationSlots;
}
