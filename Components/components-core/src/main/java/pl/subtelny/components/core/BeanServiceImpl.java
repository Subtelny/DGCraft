package pl.subtelny.components.core;

import java.util.List;
import java.util.Map;
import pl.subtelny.components.api.BeanService;

public class BeanServiceImpl implements BeanService {


	@Override
	public void initializeBeans(String context, ClassLoader classLoader) {

	}

	@Override
	public <T> T initializePrototypeBean(String context, Class<T> clazz) {
		return null;
	}

	@Override
	public <T> List<T> getBeans(String context, Class<T> clazz) {
		return null;
	}

	@Override
	public <T> T getBean(String context, String beanName, Class<T> clazz) {
		return null;
	}

	@Override
	public <T> T getBean(String context, Class<?> clazz) {
		return null;
	}

	@Override
	public Object getBean(String context, String beanName) {
		return null;
	}

	@Override
	public Map<String, Object> getBeans(String context) {
		return null;
	}
}
