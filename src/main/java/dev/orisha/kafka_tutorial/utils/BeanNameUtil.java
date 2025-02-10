package dev.orisha.kafka_tutorial.utils;

import dev.orisha.kafka_tutorial.utils.constants.ServiceConstants;

public final class BeanNameUtil {

    private BeanNameUtil() { }

    public static String getNotificationServiceBeanName(final String type) {
        return getNormalizedType(type) + ServiceConstants.NOTIFICATION_SERVICE;
    }

    public static String getNotificationServiceSimpleName(final String type) {
        String normalizedType = getNormalizedType(type);
        return normalizedType.isEmpty()
                ? ServiceConstants.NOTIFICATION_SERVICE
                : Character.toUpperCase(normalizedType.charAt(0))
                    + normalizedType.substring(1)
                    + ServiceConstants.NOTIFICATION_SERVICE;
    }

    private static String getNormalizedType(String type) {
        return (type == null || type.isBlank()) ? "" : type.strip().toLowerCase();
    }

}
