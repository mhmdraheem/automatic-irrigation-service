package com.banquemisr.irrigationservice.plot.repository;

import com.banquemisr.irrigationservice.plot.entity.PlotIrrigationSlot;
import com.banquemisr.irrigationservice.plot.entity.projection.PlotIrrigationTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlotIrrigationSlotRepository extends JpaRepository<PlotIrrigationSlot, Long> {

    @Query("""
            select new com.banquemisr.irrigationservice.plot.entity.projection.PlotIrrigationTask(
                slot.id as slotId,
                slot.startTime as startTime,
                slot.endTime as endTime,
                slot.amountLiters as amountLiters,
                slot.plot.code as plotCode
            )
            from PlotIrrigationSlot slot where now() between slot.startTime and slot.endTime and slot.status=READY
            """)
    List<PlotIrrigationTask> findIrrigationTasks();

    @Modifying
    @Query("update PlotIrrigationSlot set status=:status where id=:id")
    void updateSlotStatusById(@Param("status") PlotIrrigationSlot.Status status, @Param("id") Long slotId);

    @Modifying
    @Query("update PlotIrrigationSlot set status=READY where status in (IN_PROGRESS, FAILED) and now() > endTime")
    void resetStatusToReadyIfEndTimePassed();
}
