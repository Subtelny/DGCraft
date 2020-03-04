package pl.subtelny.utilities.collection;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CollectionUtil {

    public static boolean isCollection(Class clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    public static Collection<?> streamToCollectionByType(Class collectionType, Stream<?> stream) {
        if (List.class.isAssignableFrom(collectionType)) {
            return stream.collect(Collectors.toList());
        }
        if (Set.class.isAssignableFrom(collectionType)) {
            return stream.collect(Collectors.toSet());
        }
        throw new IllegalArgumentException("Not found strategy for collection type " + collectionType);
    }

}
