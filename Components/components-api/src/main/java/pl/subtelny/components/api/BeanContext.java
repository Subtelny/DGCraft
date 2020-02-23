package pl.subtelny.components.api;

import java.util.List;
import java.util.Map;

public final class BeanContext {

	private static BeanContext context;

	private final BeanService beanService;

	public BeanContext() {
		this.beanService = new BeanServiceImpl();
	}

	public BeanService getBeanService() {
		return beanService;
	}

	public static void initializeBeans(String context, ClassLoader classLoader) {
		getContext().getBeanService().initializeBeans(context, classLoader);
	}

	public static <T> T initializePrototypeBean(String context, Class<T> clazz) {
		return getContext().getBeanService().initializePrototypeBean(context, clazz);
	}

	public static <T> List<T> getBeans(String context, Class<T> clazz) {
		return getContext().getBeanService().getBeans(context, clazz);
	}

	public static <T> T getBean(String context, String beanName, Class<T> clazz) {
		return getContext().getBeanService().getBean(context, beanName, clazz);
	}

	public static <T> T getBean(String context, Class<?> clazz) {
		return getContext().getBeanService().getBean(context, clazz);
	}

	public static Object getBean(String context, String beanName) {
		return getContext().getBeanService().getBean(context, beanName);
	}

	public static Map<String, Object> getBeans(String context) {
		return getContext().getBeanService().getBeans(context);
	}

	private static BeanContext getContext() {
		if (context == null) {
			context = new BeanContext();
		}
		return context;
	}

}
