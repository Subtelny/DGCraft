package pl.subtelny.components.core;

import java.util.Map;

public class Test {

    @org.junit.Test
    public void test() {
        BeanLoader beanLoader = new BeanLoader();
        String packageName = this.getClass().getPackageName();
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        Map<String, Object> stringObjectMap = beanLoader.loadBeans(packageName, systemClassLoader);
        stringObjectMap.forEach((s, o) -> {
            System.out.println(s + " - " + o);
        });
    }

}
