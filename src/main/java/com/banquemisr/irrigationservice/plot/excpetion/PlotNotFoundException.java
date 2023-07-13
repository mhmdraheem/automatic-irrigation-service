package com.banquemisr.irrigationservice.plot.excpetion;

import com.banquemisr.irrigationservice.common.exception.type.BusinessException;

public class PlotNotFoundException extends BusinessException {
    public PlotNotFoundException(String message) {
        super(message);
    }
}
