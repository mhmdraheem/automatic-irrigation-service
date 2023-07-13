package com.banquemisr.irrigationservice.common.exception.message;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ErrorMessageService {

    public static final String DEFAULT_ERROR_MESSAGE = "Something wrong happened, please try again later";
    private final ErrorMessageRepository errorMessageRepository;

    @Cacheable("errorMessagesCache")
    public String getMessageForCode(String errorCode) {
        Optional<ErrorMessage> errorMessage = errorMessageRepository.findById(errorCode);
        if(errorMessage.isPresent()) {
            return errorMessage.get().getErrorMessage();
        } else {
            return DEFAULT_ERROR_MESSAGE;
        }
    }
}
