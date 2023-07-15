package com.banquemisr.irrigationservice.common.mail;

import lombok.Data;

@Data
public class Mail {
    private String to;
    private String subject;
    private String message;
}
