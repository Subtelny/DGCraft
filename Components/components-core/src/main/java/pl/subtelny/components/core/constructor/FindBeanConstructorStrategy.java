package pl.subtelny.components.core.constructor;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Optional;

public interface FindBeanConstructorStrategy {

    Optional<Constructor> findConstructor(List<Constructor> constructors);

}