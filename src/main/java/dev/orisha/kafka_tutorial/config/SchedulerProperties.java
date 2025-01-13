package dev.orisha.kafka_tutorial.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "scheduler")
@Setter
@Getter
@RefreshScope
public class SchedulerProperties {

    private int poolSize;
    private String threadNamePrefix;
    private int awaitTerminationSeconds;
    private boolean daemon;

}
