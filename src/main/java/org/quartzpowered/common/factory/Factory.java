package org.quartzpowered.common.factory;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import lombok.Getter;
import org.quartzpowered.engine.Component;

public class Factory<T> {
    @Getter
    private final Class<T> type;
    private final ConstructorAccess<T> constructorAccess;

    public Factory(Class<T> type) {
        this.type = type;
        this.constructorAccess = ConstructorAccess.get(type);
    }

    public T create() {
        return constructorAccess.newInstance();
    }
}
