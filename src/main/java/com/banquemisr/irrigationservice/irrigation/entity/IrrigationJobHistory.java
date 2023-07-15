package com.banquemisr.irrigationservice.irrigation.entity;

import com.banquemisr.irrigationservice.plot.entity.PlotIrrigationSlot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "irrigation_job_history")
@Getter
@Setter
public class IrrigationJobHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "job_start_time")
    private LocalDateTime jobStartTime;

    @NotNull
    @Column(name = "job_number")
    private String jobNumber;

    @NotNull
    @Column(name = "task_end_time")
    private LocalDateTime taskEndTime;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id", referencedColumnName = "id")
    private PlotIrrigationSlot slot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;


    public enum Status {
        SUCCESS,
        FAILED
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IrrigationJobHistory that = (IrrigationJobHistory) o;
        return Objects.equals(jobNumber, that.jobNumber) && Objects.equals(slot, that.slot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobNumber, slot);
    }
}
