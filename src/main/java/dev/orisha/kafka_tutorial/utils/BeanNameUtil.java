package dev.orisha.kafka_tutorial.utils;

public final class BeanNameUtil {

    private BeanNameUtil() { }

    public static String getNotificationServiceBeanName(final String type) {
        String string = type == null ? "" : type.toLowerCase();
        return "%sNotificationService".formatted(string);
    }
}
