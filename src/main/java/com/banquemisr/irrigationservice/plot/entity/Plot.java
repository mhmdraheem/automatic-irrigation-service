package com.banquemisr.irrigationservice.plot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "plot")
@Getter
@Setter
public class Plot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "code")
    private String code;

    @Column(name = "area")
    private String area;

    @Column(name = "crop_type")
    private String cropType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plot plot = (Plot) o;
        return Objects.equals(code, plot.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
