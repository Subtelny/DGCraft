package pl.subtelny.components.api;

import java.util.List;
import java.util.Map;

public interface BeanService {

	void initializeBeans(BeanContextId contextId, String path);

	<T> T initializePrototypeBean(BeanContextId contextId, Class<T> clazz);

	<T> List<T> getBeans(BeanContextId contextId, Class<T> clazz);

	<T> T getBean(BeanContextId contextId, String beanName, Class<T> clazz);

	<T> T getBean(BeanContextId contextId, Class<?> clazz);

	Object getBean(BeanContextId contextId, String beanName);

	Map<String, Object> getBeans(BeanContextId contextId);

}
