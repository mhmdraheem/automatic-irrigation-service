package com.banquemisr.irrigationservice.plot.excpetion;

import com.banquemisr.irrigationservice.common.exception.type.BusinessException;

public class PlotAlreadyExistsException extends BusinessException {
    public PlotAlreadyExistsException(String message) {
        super(message);
    }
}
