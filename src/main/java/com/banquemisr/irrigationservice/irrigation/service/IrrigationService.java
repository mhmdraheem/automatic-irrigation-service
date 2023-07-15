package com.banquemisr.irrigationservice.irrigation.service;

import com.banquemisr.irrigationservice.irrigation.entity.IrrigationJobHistory;
import com.banquemisr.irrigationservice.irrigation.job.Job;
import com.banquemisr.irrigationservice.irrigation.repository.IrrigationTaskHistoryRepository;
import com.banquemisr.irrigationservice.plot.entity.PlotIrrigationSlot;
import com.banquemisr.irrigationservice.plot.entity.projection.PlotIrrigationTask;
import com.banquemisr.irrigationservice.plot.repository.PlotIrrigationSlotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.banquemisr.irrigationservice.plot.entity.PlotIrrigationSlot.Status;

@Slf4j
@Service
public class IrrigationService {

    private final IrrigationApiClient irrigationApiClient;
    private final IrrigationTaskHistoryRepository irrigationTaskHistoryRepository;
    private final PlotIrrigationSlotRepository irrigationSlotRepository;
    private final IrrigationService selfRef;

    public IrrigationService(IrrigationApiClient irrigationApiClient,
                             IrrigationTaskHistoryRepository irrigationTaskHistoryRepository,
                             PlotIrrigationSlotRepository irrigationSlotRepository,
                             @Lazy IrrigationService selfRef) {
        this.irrigationApiClient = irrigationApiClient;
        this.irrigationTaskHistoryRepository = irrigationTaskHistoryRepository;
        this.irrigationSlotRepository = irrigationSlotRepository;
        this.selfRef = selfRef;
    }

    public void startIrrigation(Job job) {
        List<PlotIrrigationTask> tasks = getIrrigationTasks();
        job.setTasks(tasks);
        sendToSensorApi(job);
    }

    private List<PlotIrrigationTask> getIrrigationTasks() {
        List<PlotIrrigationTask> plotPlotIrrigationTasks = irrigationSlotRepository.findIrrigationTasks();
        log.info("{} plots will be irrigated: {}", plotPlotIrrigationTasks.size(), plotPlotIrrigationTasks);
        return plotPlotIrrigationTasks;
    }

    private void sendToSensorApi(Job job) {
        job.getTasks().forEach(task ->
                irrigationApiClient.postTask(task).subscribe(
                        success -> selfRef.handleSuccessfulTask(task, job.getInfo()),
                        error -> selfRef.handleFailedTask(task, error, job.getInfo())
                )
        );
    }

    @Transactional
    public void handleSuccessfulTask(PlotIrrigationTask task, Job.Info jobInfo) {
        log.info("plot/slot: [{}/{}] started irrigation", task.getPlotCode(), task.getSlotId());
        updateSlotStatus(task.getSlotId(), Status.IN_PROGRESS);
        logTask(task.getSlotId(), IrrigationJobHistory.Status.SUCCESS, jobInfo);
    }

    private void updateSlotStatus(Long slotId, PlotIrrigationSlot.Status newStatus) {
        irrigationSlotRepository.updateSlotStatusById(newStatus, slotId);
    }

    private void logTask(Long slotId, IrrigationJobHistory.Status status, Job.Info jobInfo) {
        IrrigationJobHistory irrigationJobHistory = createJobHistoryEntity(slotId, status, jobInfo);
        saveHistory(irrigationJobHistory);
    }

    private IrrigationJobHistory createJobHistoryEntity(Long slotId, IrrigationJobHistory.Status status, Job.Info jobInfo) {
        IrrigationJobHistory irrigationJobHistory = new IrrigationJobHistory();
        irrigationJobHistory.setSlot(getSlotReference(slotId));
        irrigationJobHistory.setStatus(status);
        irrigationJobHistory.setTaskEndTime(LocalDateTime.now());

        irrigationJobHistory.setJobStartTime(jobInfo.getStartTime());
        irrigationJobHistory.setJobNumber(jobInfo.getNumber());
        return irrigationJobHistory;
    }

    private PlotIrrigationSlot getSlotReference(Long slotId) {
        return irrigationSlotRepository.getReferenceById(slotId);
    }

    private void saveHistory(IrrigationJobHistory irrigationJobHistory) {
        irrigationTaskHistoryRepository.save(irrigationJobHistory);
    }

    @Transactional
    public void handleFailedTask(PlotIrrigationTask task, Throwable error, Job.Info jobInfo) {
        log.error("plot/slot: [{}/{}] failed to start irrigation", task.getPlotCode(), task.getSlotId(), error);
        updateSlotStatus(task.getSlotId(), Status.FAILED);
        logTask(task.getSlotId(), IrrigationJobHistory.Status.FAILED, jobInfo);
    }

    @Transactional
    public void resetFinishedIrrigationSlots() {
        irrigationSlotRepository.resetStatusToReadyIfEndTimePassed();
    }
}
