package com.banquemisr.irrigationservice.irrigation.exception;

public class SensorAPIFailedException extends RuntimeException {
    public SensorAPIFailedException(Throwable cause) {
        super(cause);
    }
}