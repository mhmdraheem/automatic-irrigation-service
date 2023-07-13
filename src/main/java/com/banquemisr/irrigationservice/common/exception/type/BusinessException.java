package com.banquemisr.irrigationservice.common.exception.type;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
