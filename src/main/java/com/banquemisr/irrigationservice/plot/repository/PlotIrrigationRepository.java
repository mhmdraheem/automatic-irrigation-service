package com.banquemisr.irrigationservice.plot.repository;

import com.banquemisr.irrigationservice.plot.entity.PlotIrrigationSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlotIrrigationRepository extends JpaRepository<PlotIrrigationSlot, Long> {
}
