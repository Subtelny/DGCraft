package pl.subtelny.components.core.api;

import pl.subtelny.utilities.BasicIdentity;

public class BeanContextId extends BasicIdentity<String> {

    public BeanContextId(String contextId) {
        super(contextId);
    }

    public static BeanContextId of(String contextId) {
        return new BeanContextId(contextId);
    }

}
