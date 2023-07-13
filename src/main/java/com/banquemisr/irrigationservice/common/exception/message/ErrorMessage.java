package com.banquemisr.irrigationservice.common.exception.message;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "error_message")
@Getter
@Setter
public class ErrorMessage {

    @Id
    @NotBlank
    @Column(name = "error_type")
    private String errorType;

    @NotBlank
    @Column(name = "error_message")
    private String errorMessage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorMessage that = (ErrorMessage) o;
        return Objects.equals(errorType, that.errorType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorType);
    }
}
