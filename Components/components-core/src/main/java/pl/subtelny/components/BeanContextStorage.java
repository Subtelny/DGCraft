package pl.subtelny.components;

import com.google.common.collect.Maps;
import pl.subtelny.components.api.BeanContextId;

import java.util.Map;

public class BeanContextStorage {

    private Map<BeanContextId, BeanStorage> beanStorages = Maps.newConcurrentMap();

    public void setBeanStorage(BeanContextId contextId, BeanStorage beanStorage) {
        beanStorages.computeIfAbsent(contextId, contextId1 -> beanStorage);
    }

    public BeanStorage getBeanStorage(BeanContextId contextId) {
        BeanStorage beanStorage = beanStorages.get(contextId);
        if (beanStorage == null) {
            throw new IllegalArgumentException(String.format("Not found beanStorage for contextId %s", contextId));
        }
        return beanStorage;
    }

}
