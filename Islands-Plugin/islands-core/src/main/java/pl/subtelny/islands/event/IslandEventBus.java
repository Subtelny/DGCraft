package pl.subtelny.islands.event;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class IslandEventBus {

    private static IslandEventBus INSTANCE;

    private final Map<Class<? extends IslandEvent>, List<IslandEventListener>> listeners;

    @Autowired
    public IslandEventBus(List<IslandEventListener> listeners) {
        INSTANCE = this;
        this.listeners = mapListenersPerEvent(listeners);
    }

    private <T extends IslandEvent> List<IslandEventListener<T>> getListeners(T event) {
        Class<? extends IslandEvent> clazz = event.getClass();
        List<IslandEventListener> islandEventListeners = listeners.get(clazz);
        return islandEventListeners.stream()
                .map(islandEventListener -> (IslandEventListener<T>) islandEventListener)
                .collect(Collectors.toList());
    }

    private Map<Class<? extends IslandEvent>, List<IslandEventListener>> mapListenersPerEvent(List<IslandEventListener> listeners) {
        return listeners.stream()
                .collect(Collectors.groupingBy(islandEventListener -> getGenericTypeOf(islandEventListener.getClass())));
    }

    private <T extends IslandEventListener> Class<? extends IslandEvent> getGenericTypeOf(Class<T> clazz) {
        Type type = clazz.getAnnotatedInterfaces()[0].getType();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            return (Class<? extends IslandEvent>) typeArguments[0];
        }
        return null;
    }

    public static <T extends IslandEvent> void call(T event) {
        List<IslandEventListener<T>> listeners = INSTANCE.getListeners(event);
        listeners.forEach(islandEventListener -> islandEventListener.handle(event));
    }

}
