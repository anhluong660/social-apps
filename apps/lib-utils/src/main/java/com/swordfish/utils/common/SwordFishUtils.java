package com.swordfish.utils.common;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class SwordFishUtils {

    public static void logError(String message) {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.ms");

        String log = String.format("%s ERROR [%s]: %s", time.format(formatter), Thread.currentThread().getName(), message);
        System.out.println(log);
    }

    private static boolean isBasicType(Class<?> clazz) {
        return Number.class.isAssignableFrom(clazz)
                || clazz == String.class
                || clazz == Date.class
                || clazz == LocalDate.class
                || clazz == LocalDateTime.class
                ;
    }

    private static void copyBasicValue(Field field, Object source, Object target) {
        try {
            field.setAccessible(true);
            Object value = field.get(source);

            Field targetField = target.getClass().getDeclaredField(field.getName());
            targetField.setAccessible(true);
            targetField.set(target, value);
        } catch (Exception ignore) {

        }
    }

    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        try {
            T target = targetClass.newInstance();

            Class<?> sourceClass = source.getClass();
            Field[] fields = sourceClass.getDeclaredFields();

            for (Field field : fields) {
                if (isBasicType(field.getType())) {
                    copyBasicValue(field, source, target);
                }
            }

            return target;
        } catch (Exception e) {
            logError("SampleUtils:copyProperties Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
