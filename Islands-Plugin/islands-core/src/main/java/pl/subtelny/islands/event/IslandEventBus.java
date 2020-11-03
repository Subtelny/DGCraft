package pl.subtelny.islands.event;

import pl.subtelny.components.core.api.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class IslandEventBus {

    private static IslandEventBus instance;

    private final Map<Class<? extends IslandEvent>, List<IslandEventListener<? extends IslandEvent>>> listeners;

    public IslandEventBus(List<IslandEventListener<?>> listeners) {
        instance = this;
        this.listeners = mapListenersPerEvent(listeners);
    }

    private  <T extends IslandEvent> List<IslandEventListener<? extends IslandEvent>> getListeners(T event) {
        Class<? extends IslandEvent> clazz = event.getClass();
        return listeners.get(clazz);
    }

    private Map<Class<? extends IslandEvent>, List<IslandEventListener<?>>> mapListenersPerEvent(List<IslandEventListener<?>> listeners) {
        return listeners.stream()
                .collect(Collectors.groupingBy(islandEventListener -> getGenericTypeOf(islandEventListener.getClass())));
    }

    private <T extends IslandEventListener> Class<? extends IslandEvent> getGenericTypeOf(Class<T> clazz) {
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            return (Class<? extends IslandEvent>) typeArguments[0];
        }
        return null;
    }

    public static <T extends IslandEvent> void call(T event) {
        List<IslandEventListener<? extends IslandEvent>> listeners = instance.getListeners(event);
        listeners.forEach(islandEventListener -> {
            IslandEventListener<T> listener = (IslandEventListener<T>) islandEventListener;
            listener.handle(event);
        });
    }

}
