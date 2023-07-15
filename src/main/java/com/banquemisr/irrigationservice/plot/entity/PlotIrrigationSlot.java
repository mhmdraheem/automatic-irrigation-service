package com.banquemisr.irrigationservice.plot.entity;

import com.banquemisr.irrigationservice.irrigation.entity.IrrigationJobHistory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "plot_irrigation_slot")
@Getter
@Setter
public class PlotIrrigationSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_time")
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time")
    private LocalTime endTime;

    @NotNull
    @Positive
    @Column(name = "amount_liters")
    private Integer amountLiters;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plot_id", referencedColumnName = "id")
    private Plot plot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.READY;

    @OneToMany(mappedBy = "slot", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<IrrigationJobHistory> irrigationTaskHistories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlotIrrigationSlot that = (PlotIrrigationSlot) o;
        return Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }

    public enum Status {
        READY,
        IN_PROGRESS,
        FAILED,
        DELETED
    }
}
