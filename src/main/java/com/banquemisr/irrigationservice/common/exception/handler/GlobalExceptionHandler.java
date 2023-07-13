package com.banquemisr.irrigationservice.common.exception.handler;

import com.banquemisr.irrigationservice.common.exception.message.ErrorMessageService;
import com.banquemisr.irrigationservice.common.exception.response.ErrorResponse;
import com.banquemisr.irrigationservice.common.exception.type.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorMessageService errorMessageService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericError(Exception ex) {
        log.error("Exception {} has been caught", ex.getClass().getSimpleName(), ex);
        return createErrorResponse(ex);
    }

    private ErrorResponse createErrorResponse(Exception ex) {
        String errorCode = ex.getClass().getSimpleName();
        String errorMessage = errorMessageService.getMessageForCode(errorCode);
        return new ErrorResponse(errorCode, errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidMethodArgsError(MethodArgumentNotValidException ex) {
        log.error("Exception {} has been caught", ex.getClass().getSimpleName(), ex);

        String errors = ex.getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("- "));

        return new ErrorResponse("500", errors);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBusinessError(BusinessException ex) {
        log.error("Exception {} has been caught", ex.getClass().getSimpleName(), ex);
        return createErrorResponse(ex);
    }
}
