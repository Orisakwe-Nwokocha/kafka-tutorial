package dev.orisha.kafka_tutorial.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
@Slf4j
public class SchedulerConfig {

    private final SchedulerProperties schedulerProperties;

    @Autowired
    public SchedulerConfig(SchedulerProperties schedulerProperties) {
        this.schedulerProperties = schedulerProperties;
    }

    @Bean(name = "kafkaProducerScheduler")
    public ThreadPoolTaskScheduler kafkaProducerScheduler() {
        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(schedulerProperties.getPoolSize());
        scheduler.setThreadNamePrefix(schedulerProperties.getThreadNamePrefix());
        scheduler.setDaemon(schedulerProperties.isDaemon());
        scheduler.setAwaitTerminationSeconds(schedulerProperties.getAwaitTerminationSeconds());
        return scheduler;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        log.info("Creating task scheduler using custom thread pool");
        return kafkaProducerScheduler();
    }

//    @PostConstruct
//    public void initialize() {
//        log.info("Scheduler bean initialized with the following settings:");
//        log.info("Pool Size: {}", schedulerProperties.getPoolSize());
//        log.info("Thread Name Prefix: {}", schedulerProperties.getThreadNamePrefix());
//        log.info("Daemon: {}", schedulerProperties.isDaemon());
//        log.info("Await Termination Seconds: {}", schedulerProperties.getAwaitTerminationSeconds());
//    }

}
