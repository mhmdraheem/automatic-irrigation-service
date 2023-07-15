package com.banquemisr.irrigationservice.plot.repository;

import com.banquemisr.irrigationservice.plot.entity.PlotIrrigationSlot;
import com.banquemisr.irrigationservice.plot.entity.projection.PlotIrrigationTask;
import jakarta.persistence.Tuple;
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

    @Query(value = """
            select
            	AVG(slot.amount_liters) as avgLiters,
            	MIN(TIMEDIFF(slot.end_time, slot.start_time)) as minSlotTime,
            	MAX(TIMEDIFF(slot.end_time, slot.start_time)) as maxSlotTime,
            	SEC_TO_TIME(AVG(TIME_TO_SEC(TIMEDIFF(slot.end_time, slot.start_time)))) as avgSlotTime
            from plot plot join plot_irrigation_slot slot on plot.id = slot.plot_id
            where plot.area=:plotArea and plot.crop_type=:cropType
            group by plot.area, plot.crop_type
            """, nativeQuery = true)
    Tuple predictPlotIrrigation(@Param("plotArea") String plotArea, @Param("cropType") String cropType);
}
