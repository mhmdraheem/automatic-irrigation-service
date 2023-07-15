package com.banquemisr.irrigationservice.plot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
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

    @NotBlank
    @Column(name = "area")
    private String area;

    @NotBlank
    @Column(name = "crop_type")
    private String cropType;

    @OneToMany(mappedBy = "plot", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PlotIrrigationSlot> irrigationSlots = new ArrayList<>();

    public void configureIrrigationSlots(List<PlotIrrigationSlot> slots) {
        if(slots != null) {
            this.irrigationSlots.forEach(slot -> slot.setStatus(PlotIrrigationSlot.Status.DELETED));
            for (PlotIrrigationSlot irrigation : slots) {
                irrigation.setPlot(this);
                this.irrigationSlots.add(irrigation);
            }
        }
    }

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
