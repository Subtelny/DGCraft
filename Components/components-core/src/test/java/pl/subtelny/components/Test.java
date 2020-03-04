package pl.subtelny.components;

import pl.subtelny.components.api.BeanContext;

import java.util.Map;

public class Test {

    @org.junit.Test
    public void test() {
        BeanContext.initializeContext(new BeanServiceImpl());
        BeanContext.initializeBeans("test", this.getClass().getPackageName());
        Map<String, Object> test = BeanContext.getBeans("test");

    }

}
