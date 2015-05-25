package org.quartzpowered.common.factory;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.google.inject.Injector;
import lombok.Getter;

public class Factory<T> {
    private final Injector injector;

    @Getter
    private final Class<T> type;
    private final ConstructorAccess<T> constructorAccess;

    public Factory(Injector injector, Class<T> type) {
        this.injector = injector;
        this.type = type;
        this.constructorAccess = ConstructorAccess.get(type);
    }

    public T create() {
        T instance = constructorAccess.newInstance();
        injector.injectMembers(instance);
        return instance;
    }
}
