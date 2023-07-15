package com.banquemisr.irrigationservice.irrigation.service;

import com.banquemisr.irrigationservice.irrigation.exception.SensorAPIFailedException;
import com.banquemisr.irrigationservice.irrigation.exception.ServerException;
import com.banquemisr.irrigationservice.plot.entity.projection.PlotIrrigationTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientRequest;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
public class IrrigationApiClient {

    private final WebClient webClient;
    private final Environment environment;

    public Mono<ResponseEntity<Void>> postTask(PlotIrrigationTask task) {
        log.info("Started sending request for plot/slot: [{}/{}]", task.getPlotCode(), task.getSlotId());
        return webClient
                .post()
                .uri("/irrigate")
                .httpRequest(configureRequest())
                .body(Mono.just(task), PlotIrrigationTask.class)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(ServerException::new))
                .toBodilessEntity()
                .retryWhen(retrySpecs(task));
    }

    private Consumer<ClientHttpRequest> configureRequest() {
        return httpRequest -> {
            HttpClientRequest reactorRequest = httpRequest.getNativeRequest();
            reactorRequest.responseTimeout(Duration.ofSeconds(responseTimeout()));
        };
    }

    private Integer responseTimeout() {
        return environment.getRequiredProperty("irrigation.api.response-timeout", Integer.class);
    }

    private RetryBackoffSpec retrySpecs(PlotIrrigationTask task) {
        return Retry.backoff(maxAttempts(), Duration.of(backoff(), ChronoUnit.SECONDS))
                .filter(retriedExceptions())
                .doBeforeRetry(retrySignal -> {
                    log.info("retrying task {} -> round: {}", task.getSlotId(), retrySignal.totalRetries() + 1);
                })
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                    throw new SensorAPIFailedException(retrySignal.failure());
                });
    }

    private Predicate<Throwable> retriedExceptions() {
        // only retry for server errors or timeout/connection errors
        return throwable -> throwable instanceof ServerException || throwable instanceof WebClientRequestException;
    }

    private Integer maxAttempts() {
        return environment.getRequiredProperty("irrigation.api.retry.max-attempts", Integer.class);
    }

    private Integer backoff() {
        return environment.getRequiredProperty("irrigation.api.retry.backoff", Integer.class);
    }
}
