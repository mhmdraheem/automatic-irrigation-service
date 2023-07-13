package com.banquemisr.irrigationservice.common.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // cache request/response to be re-read again
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long start = System.currentTimeMillis();
        filterChain.doFilter(requestWrapper, responseWrapper);

        long end = System.currentTimeMillis();
        long durationSeconds = (end - start) / 1000;

        log.info("Finished processing request: [{} {}] with body=[{}] and params=[{}] in {} seconds. Response: status=[{}] body=[{}]",
                requestWrapper.getMethod(),
                requestWrapper.getRequestURI(),
                new String(requestWrapper.getContentAsByteArray()),
                requestWrapper.getQueryString(),
                durationSeconds,
                responseWrapper.getStatus(),
                new String(responseWrapper.getContentAsByteArray()));

        responseWrapper.copyBodyToResponse();
    }
}
