package org.quartzpowered.common.inject.logger;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.slf4j.Logger;

import java.lang.reflect.Field;

public class LoggerTypeListener implements TypeListener {
    @Override
    public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> encounter) {
        Class<?> type = typeLiteral.getRawType();
        while (type != null) {
            for (Field field : type.getDeclaredFields()) {
                if (field.getType() == Logger.class) {
                    encounter.register(new LoggerMembersInjector<I>(field));
                }
            }
            type = type.getSuperclass();
        }
    }
}
