package org.quartzpowered.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionUtil {
    public static void setFinalField(Field field, Object instance, Object value) throws ReflectiveOperationException {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(instance, value);
    }

    public static Field getConstantField(Class<?> type, String name) {
        try {
            return type.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
