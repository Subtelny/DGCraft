package pl.subtelny.components.core.api;

import java.util.List;
import java.util.Map;

public interface BeanService {

	void initializeBeans(ClassLoader classLoader, List<String> paths);

	<T> T initializePrototypeBean(Class<T> clazz);

	<T> List<T> getBeans(Class<T> clazz);

	<T> T getBean(Class<T> clazz);

	Map<Class, Object> getBeans();

}
