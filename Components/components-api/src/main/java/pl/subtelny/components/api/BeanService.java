package pl.subtelny.components.api;

import java.util.List;
import java.util.Map;

public interface BeanService {

	void initializeBeans(String context, ClassLoader classLoader);

	<T> T initializePrototypeBean(String context, Class<T> clazz);

	<T> List<T> getBeans(String context, Class<T> clazz);

	<T> T getBean(String context, String beanName, Class<T> clazz);

	<T> T getBean(String context, Class<?> clazz);

	Object getBean(String context, String beanName);

	Map<String, Object> getBeans(String context);

}
