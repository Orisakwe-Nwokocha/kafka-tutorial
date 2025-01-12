package dev.orisha.kafka_tutorial.utils;

public final class BeanNameUtil {

    private BeanNameUtil() { }

    public static String getNotificationServiceBeanName(String type) {
        return "%sNotificationService".formatted(type.toLowerCase());
    }
}
