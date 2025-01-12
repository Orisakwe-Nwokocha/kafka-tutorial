package dev.orisha.kafka_tutorial.config;

import dev.orisha.kafka_tutorial.services.NotificationService;
import dev.orisha.kafka_tutorial.utils.BeanNameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class NotificationServiceConfig {

    private final ApplicationContext applicationContext;
    private final NotificationService defaultNotificationService;

    @Autowired
    public NotificationServiceConfig(final ApplicationContext applicationContext,
                                     final NotificationService defaultNotificationService) {
        this.applicationContext = applicationContext;
        this.defaultNotificationService = defaultNotificationService;
    }

    @Bean
    public NotificationService notificationService(@Value("${notification.type}") final String type) {
        String beanName = BeanNameUtil.getNotificationServiceBeanName(type);
        log.info("Configuring notification service bean");
        if (applicationContext != null && applicationContext.containsBean(beanName)) {
            log.info("Done configuring notification service bean: '{}'", beanName);
            return applicationContext.getBean(beanName, NotificationService.class);
        }
        log.warn("Could not find notification service bean. Configuring default notification service");
        return this.defaultNotificationService;
    }

}
