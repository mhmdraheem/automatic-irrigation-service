package com.banquemisr.irrigationservice.irrigation.alerting;

import com.banquemisr.irrigationservice.common.mail.Mail;
import com.banquemisr.irrigationservice.common.mail.MailService;
import com.banquemisr.irrigationservice.plot.entity.projection.PlotIrrigationTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertingService {

    private final MailService mailService;

    @Value("${irrigation.alerting.email}")
    private String alertingEmail;

    @Async
    @Retryable(maxAttemptsExpression = "${irrigation.alerting.retry.max-attempts}",
            backoff = @Backoff(delayExpression="${irrigation.alerting.retry.backoff}"))
    public void sendIrrigationFailureAlertAsync(PlotIrrigationTask task) {
        Mail mail = createAlertMail(task);
        sendMail(mail);
    }

    private Mail createAlertMail(PlotIrrigationTask task) {
        Mail mail = new Mail();
        mail.setTo(alertingEmail);
        mail.setSubject("Irrigation Failed!");
        mail.setMessage(String.format("An error occurred during irrigating plot with code: %s at slot slot [%s-%s] ",
                task.getPlotCode(),
                task.getStartTime(),
                task.getEndTime()));
        return mail;
    }

    private void sendMail(Mail mail) {
        log.info("creating an alert for irrigation failure");
        mailService.sendEmail(mail);
    }

    @Recover
    public void recoverAlertSendingError(Exception ex, PlotIrrigationTask task) {
        log.error("Failed to send alert for slot: {}", task.getSlotId(), ex);
    }
}
