package dev.orisha.kafka_tutorial.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
//@RefreshScope
@Slf4j
public class ConfigClientController {

    //    @Value("${message}")
//    private String message;

    private final RestTemplate restTemplate;
//    private final SchedulerProperties schedulerProperties;

    @Autowired
    public ConfigClientController(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
//        this.schedulerProperties = schedulerProperties;
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

    //    @GetMapping("/message")
//    public ConfigClientResponse getMessage() {
//        Map<String, Object> map = new TreeMap<>();
//        map.put("poolSize", schedulerProperties.getPoolSize());
//        map.put("threadNamePrefix", schedulerProperties.getThreadNamePrefix());
//        map.put("awaitTerminationSeconds", schedulerProperties.getAwaitTerminationSeconds());
//        map.put("daemon", schedulerProperties.isDaemon());
//        String response = String.format("%n%s: %s%n%s: %s", "message", message, "data", map);
//        log.info("API response: {}", response);
//        return new ConfigClientResponse(message, map);
//    }
}