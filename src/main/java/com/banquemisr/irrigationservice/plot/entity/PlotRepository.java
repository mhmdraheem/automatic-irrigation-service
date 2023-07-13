package com.banquemisr.irrigationservice.plot.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlotRepository extends JpaRepository<Plot, Long> {
    Optional<Plot> findPlotByCode(String code);
}
