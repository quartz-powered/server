package org.quartzpowered.common.reflector;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.inject.Injector;
import lombok.Getter;
import lombok.experimental.Delegate;

public class Reflector<T> {
    @Getter
    private final Class<T> type;

    private final MethodAccess methodAccess;

    public Reflector(Class<T> type) {
        this.type = type;
        this.methodAccess = MethodAccess.get(type);
    }

    public Object invoke (Object object, String name, Object... args) {
        return methodAccess.invoke(object, name, args);
    }
}
