package pl.subtelny.components.api;

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

    public static void initializeBeans(String contextId, String path) {
        getContext().getBeanService().initializeBeans(toBeanContextId(contextId), path);
    }

    public static <T> T initializePrototypeBean(String contextId, Class<T> clazz) {
        return getContext().getBeanService().initializePrototypeBean(toBeanContextId(contextId), clazz);
    }

    public static <T> List<T> getBeans(String contextId, Class<T> clazz) {
        return getContext().getBeanService().getBeans(toBeanContextId(contextId), clazz);
    }

    public static <T> T getBean(String contextId, String beanName, Class<T> clazz) {
        return getContext().getBeanService().getBean(toBeanContextId(contextId), beanName, clazz);
    }

    public static <T> T getBean(String contextId, Class<?> clazz) {
        return getContext().getBeanService().getBean(toBeanContextId(contextId), clazz);
    }

    public static Object getBean(String contextId, String beanName) {
        return getContext().getBeanService().getBean(toBeanContextId(contextId), beanName);
    }

    public static Map<String, Object> getBeans(String contextId) {
        return getContext().getBeanService().getBeans(toBeanContextId(contextId));
    }

    private static BeanContextId toBeanContextId(String contextId) {
        return BeanContextId.of(contextId);
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
