package com.banquemisr.irrigationservice.irrigation.repository;

import com.banquemisr.irrigationservice.irrigation.entity.IrrigationJobHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IrrigationTaskHistoryRepository extends JpaRepository<IrrigationJobHistory, Long> {
}
