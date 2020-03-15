package pl.subtelny.components.core.api;

import java.util.List;
import java.util.Map;

public interface BeanService {

	void initializeBeans(BeanContextId contextId, List<String> paths);

	<T> T initializePrototypeBean(BeanContextId contextId, Class<T> clazz);

	<T> List<T> getBeans(BeanContextId contextId, Class<T> clazz);

	<T> T getBean(BeanContextId contextId, String beanName, Class<T> clazz);

	<T> T getBean(BeanContextId contextId, Class<?> clazz);

	Object getBean(BeanContextId contextId, String beanName);

	Map<String, Object> getBeans(BeanContextId contextId);

}
