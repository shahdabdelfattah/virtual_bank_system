package com.vbank.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private final KafkaLogProducer kafkaLogProducer;
    private final ObjectMapper objectMapper;

    public RequestResponseLoggingFilter(KafkaLogProducer kafkaLogProducer, ObjectMapper objectMapper) {
        this.kafkaLogProducer = kafkaLogProducer;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.contains("/h2-console") || path.contains("/swagger-ui") || path.contains("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            try {
                String requestBody = getRequestBody(requestWrapper);
                sendLog(requestBody, "Request");

                String responseBody = getResponseBody(responseWrapper);
                sendLog(responseBody, "Response");
            } catch (Exception e) {
                logger.error("Failed to generate or send Kafka logs", e);
            }

            responseWrapper.copyBodyToResponse();
        }
    }

    private void sendLog(String body, String messageType) {
        try {
            Map<String, String> logPayload = new HashMap<>();
            logPayload.put("message", body);
            logPayload.put("messageType", messageType);
            logPayload.put("dateTime", Instant.now().toString());

            String jsonLog = objectMapper.writeValueAsString(logPayload);
            kafkaLogProducer.sendLog(jsonLog);
        } catch (Exception e) {
            logger.error("Error creating JSON log payload", e);
        }
    }

    private String getRequestBody(ContentCachingRequestWrapper wrapper) {
        byte[] buf = wrapper.getContentAsByteArray();
        if (buf.length > 0) {
            return new String(buf, 0, buf.length, StandardCharsets.UTF_8);
        }
        return "";
    }

    private String getResponseBody(ContentCachingResponseWrapper wrapper) {
        byte[] buf = wrapper.getContentAsByteArray();
        if (buf.length > 0) {
            return new String(buf, 0, buf.length, StandardCharsets.UTF_8);
        }
        return "";
    }
}
