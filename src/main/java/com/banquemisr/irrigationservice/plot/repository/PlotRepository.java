package com.banquemisr.irrigationservice.plot.repository;

import com.banquemisr.irrigationservice.plot.entity.Plot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlotRepository extends JpaRepository<Plot, Long> {
    Optional<Plot> findPlotByCode(String code);

    @Query("select plot from Plot plot left join fetch plot.irrigationSlots")
    Page<Plot> findPlotWithSlots(PageRequest pageRequest);
}
