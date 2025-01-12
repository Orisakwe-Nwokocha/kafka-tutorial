package dev.orisha.kafka_tutorial.factory;

import dev.orisha.kafka_tutorial.services.NotificationService;
import dev.orisha.kafka_tutorial.utils.BeanNameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationServiceFactory {

    private final ApplicationContext applicationContext;
    private final NotificationService defaultNotificationService;

    @Autowired
    public NotificationServiceFactory(final ApplicationContext applicationContext,
                                      final NotificationService defaultNotificationService) {
        this.applicationContext = applicationContext;
        this.defaultNotificationService = defaultNotificationService;
    }

    public NotificationService getNotificationService(String notificationType) {
        String beanName = BeanNameUtil.getNotificationServiceBeanName(notificationType);
        log.info("NotificationService factory trying to load notification service bean '{}'", beanName);
        if (this.applicationContext != null && this.applicationContext.containsBean(beanName)) {
            log.info("{} bean found and fully loaded", beanName);
            return this.applicationContext.getBean(beanName, NotificationService.class);
        }
        log.warn("Invalid notification type '{}'. Falling back to default implementation", notificationType);
        return this.defaultNotificationService;
    }

}
