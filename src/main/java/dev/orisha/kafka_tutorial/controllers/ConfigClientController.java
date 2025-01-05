package dev.orisha.kafka_tutorial.controllers;

import dev.orisha.kafka_tutorial.config.SchedulerProperties;
import dev.orisha.kafka_tutorial.dto.ConfigClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.TreeMap;

@RestController
@RefreshScope
@Slf4j
public class ConfigClientController {

    @Autowired
    public ConfigClientController(RestTemplate restTemplate, SchedulerProperties schedulerProperties) {
        this.restTemplate = restTemplate;
        this.schedulerProperties = schedulerProperties;
    }


    @GetMapping("/message")
    public ConfigClientResponse getMessage() {
        Map<String, Object> map = new TreeMap<>();
        map.put("poolSize", schedulerProperties.getPoolSize());
        map.put("threadNamePrefix", schedulerProperties.getThreadNamePrefix());
        map.put("awaitTerminationSeconds", schedulerProperties.getAwaitTerminationSeconds());
        map.put("daemon", schedulerProperties.isDaemon());
        String response = String.format("%n%s: %s%n%s: %s", "message", message, "data", map);
        log.info("API response: {}", response);
        return new ConfigClientResponse(message, map);
    }

    @PutMapping("/refresh")
    public String refresh() {
        log.info("REQUEST to refresh application configurations");
        String url = "http://localhost:{port}/actuator/refresh";
        try {
            HttpEntity<String> entity = getDefaultHttpEntity();
            restTemplate.postForObject(url, entity, Void.class, port);
            log.info("Actuator refresh endpoint called successfully");
            return "Application configurations refreshed successfully";
        } catch (Exception exception) {
            log.error("Error refreshing application configurations: {}", exception.getMessage());
            return "Error refreshing application configurations";
        }
    }

    private static HttpEntity<String> getDefaultHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }

    @Value("${message}")
    private String message;

    @Value("${server.port}")
    private String port;

    private final RestTemplate restTemplate;
    private final SchedulerProperties schedulerProperties;

}