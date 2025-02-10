package dev.orisha.kafka_tutorial.factory;

import dev.orisha.kafka_tutorial.services.NotificationService;
import dev.orisha.kafka_tutorial.utils.BeanNameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
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

    public NotificationService getNotificationService(final String notificationType) {
        String beanName = BeanNameUtil.getNotificationServiceBeanName(notificationType);
        log.info("Attempting to load NotificationService bean '{}'", beanName);

        try {
            NotificationService service = this.applicationContext.getBean(beanName, NotificationService.class);
            log.info("Successfully loaded NotificationService bean '{}'", beanName);
            return service;
        } catch (BeansException exception) {
            log.warn("No bean found for '{}'. Using default NotificationService.", beanName, exception);
        }

        return this.defaultNotificationService;
    }

}
