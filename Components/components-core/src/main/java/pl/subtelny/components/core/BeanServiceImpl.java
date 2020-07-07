package pl.subtelny.components.core;

import pl.subtelny.components.core.api.BeanService;

import java.util.List;
import java.util.Map;

public class BeanServiceImpl implements BeanService {

	private final BeanStorage beanStorage = new BeanStorage();

	public BeanServiceImpl() {
	}

	@Override
	public void initializeBeans(List<ClassLoader> classLoaders, List<String> paths) {
		BeansLoader loader = new BeansLoader(paths, classLoaders);
		Map<Class, Object> loadedBeans = loader.loadBeans();
		beanStorage.addBeans(loadedBeans);
	}

	@Override
	public <T> T initializePrototypeBean(Class<T> clazz) {
		return null;
	}

	@Override
	public <T> List<T> getBeans(Class<T> clazz) {
		return beanStorage.getBeans(clazz);
	}

	@Override
	public <T> T getBean(Class<T> clazz) {
		return beanStorage.getBean(clazz);
	}

	@Override
	public Map<Class, Object> getBeans() {
		return beanStorage.getBeans();
	}
}
