package pl.subtelny.components.core;

import pl.subtelny.components.core.api.BeanContext;
import pl.subtelny.components.core.api.BeanContextId;

import java.util.Arrays;
import java.util.Map;

public class Test {

    @org.junit.Test
    public void test() {
        BeanContext.initializeContext(new BeanServiceImpl());
        BeanContextId contextId = BeanContextId.of("test");
        BeanContext.initializeBeans(contextId, Arrays.asList(this.getClass().getPackageName()));
        Map<String, Object> test = BeanContext.getBeans(contextId);

    }

}
