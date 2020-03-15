package pl.subtelny.components.core.api;

import java.util.List;
import java.util.Map;

public final class BeanContext {

    private static BeanContext context;

    private final BeanService beanService;

    public BeanContext(BeanService beanService) {
        this.beanService = beanService;
    }

    public BeanService getBeanService() {
        return beanService;
    }

    public static void initializeBeans(BeanContextId contextId, List<String> paths) {
        getContext().getBeanService().initializeBeans(contextId, paths);
    }

    public static <T> T initializePrototypeBean(BeanContextId contextId, Class<T> clazz) {
        return getContext().getBeanService().initializePrototypeBean(contextId, clazz);
    }

    public static <T> List<T> getBeans(BeanContextId contextId, Class<T> clazz) {
        return getContext().getBeanService().getBeans(contextId, clazz);
    }

    public static <T> T getBean(BeanContextId contextId, String beanName, Class<T> clazz) {
        return getContext().getBeanService().getBean(contextId, beanName, clazz);
    }

    public static <T> T getBean(BeanContextId contextId, Class<?> clazz) {
        return getContext().getBeanService().getBean(contextId, clazz);
    }

    public static Object getBean(BeanContextId contextId, String beanName) {
        return getContext().getBeanService().getBean(contextId, beanName);
    }

    public static Map<String, Object> getBeans(BeanContextId contextId) {
        return getContext().getBeanService().getBeans(contextId);
    }

    public static void initializeContext(BeanService beanService) {
        if (context != null) {
            throw new IllegalArgumentException("BeanContext is already initialized");
        }
        context = new BeanContext(beanService);
    }

    private static BeanContext getContext() {
        if (context == null) {
            throw new IllegalArgumentException("BeanContext is not initialized");
        }
        return context;
    }

}
