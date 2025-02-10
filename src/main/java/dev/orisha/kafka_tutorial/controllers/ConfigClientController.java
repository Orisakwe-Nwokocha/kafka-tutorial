package dev.orisha.kafka_tutorial.controllers;

import dev.orisha.kafka_tutorial.config.SchedulerProperties;
import dev.orisha.kafka_tutorial.dto.ConfigClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.TreeMap;

@RestController
@Slf4j
public class ConfigClientController {

    /**
     * The biggest difference of variables created with the Annotation @Value
     * is that the value will be fixed as it will be assigned to run the
     * application and never refreshed. This is the only injection
     * method that is never refreshed.
     * <p> It effectively means there is no need for @RefreshScope.
     */
    @Value("${message}")
    private String message;

    private final RestTemplate restTemplate;
    private final SchedulerProperties schedulerProperties;

    @Autowired
    public ConfigClientController(final RestTemplate restTemplate,
                                  final SchedulerProperties schedulerProperties) {
        this.restTemplate = restTemplate;
        this.schedulerProperties = schedulerProperties;
    }

    @GetMapping("/message")
    public ConfigClientResponse getMessage(@Value("${message}") String message) {
        Map<String, Object> map = new TreeMap<>();
        map.put("poolSize", schedulerProperties.getPoolSize());
        map.put("threadNamePrefix", schedulerProperties.getThreadNamePrefix());
        map.put("awaitTerminationSeconds", schedulerProperties.getAwaitTerminationSeconds());
        map.put("daemon", schedulerProperties.isDaemon());
        String response = String.format("%n%s: %s%n%s: %s", "message", message, "data", map);
        log.info("API response: {}", response);
        ConfigClientResponse configClientResponse = new ConfigClientResponse(message, map);
        log.info("API response: {}", configClientResponse);
        return configClientResponse;
    }

    @PostMapping("/refresh")
    public String refresh() {
        log.info("REST request to refresh application configurations");
        try {
            refreshConfigProps();
            return "Actuator refresh endpoint called successfully";
        } catch (Exception exception) {
            log.error("Error refreshing application configurations: {}", exception.getMessage());
            return "Error refreshing application configurations";
        }
    }

    @Scheduled(cron = "0 0 */6 * * *")
    private void refreshConfigProps() {
        HttpEntity<String> entity = getDefaultHttpEntity();
        String url = "http://localhost:8084/actuator/refresh";
        restTemplate.postForObject(url, entity, Void.class);
        log.info("Application configurations refreshed successfully");
    }

    private static HttpEntity<String> getDefaultHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }

}