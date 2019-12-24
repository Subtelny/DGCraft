package pl.subtelny.entity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class AnyChangesPropertyProxy implements InvocationHandler {

    private final Entity<?> entity;

    public AnyChangesPropertyProxy(Entity<?> entity) {
        this.entity = entity;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        entity.setAnyChanges(true);
        return method.invoke(entity, args);
    }

}
