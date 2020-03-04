package pl.subtelny.components;

import pl.subtelny.components.api.BeanContextId;
import pl.subtelny.components.api.BeanService;

import java.util.List;
import java.util.Map;

public class BeanServiceImpl implements BeanService {

	private final BeanContextStorage beanContextStorage;

	public BeanServiceImpl() {
		this.beanContextStorage = new BeanContextStorage();
	}

	@Override
	public void initializeBeans(BeanContextId contextId, String path) {
		BeansLoader loader = new BeansLoader(path);
		Map<String, Object> loadedBeans = loader.loadBeans();
		BeanStorage beanStorage = new BeanStorage(loadedBeans);
		beanContextStorage.setBeanStorage(contextId, beanStorage);
	}

	@Override
	public <T> T initializePrototypeBean(BeanContextId contextId, Class<T> clazz) {
		return null;
	}

	@Override
	public <T> List<T> getBeans(BeanContextId contextId, Class<T> clazz) {
		BeanStorage beanStorage = beanContextStorage.getBeanStorage(contextId);
		return beanStorage.getBeans(clazz);
	}

	@Override
	public <T> T getBean(BeanContextId contextId, String beanName, Class<T> clazz) {
		BeanStorage beanStorage = beanContextStorage.getBeanStorage(contextId);
		return beanStorage.getBean(beanName, clazz);
	}

	@Override
	public <T> T getBean(BeanContextId contextId, Class<?> clazz) {
		BeanStorage beanStorage = beanContextStorage.getBeanStorage(contextId);
		return beanStorage.getBean(clazz);
	}

	@Override
	public Object getBean(BeanContextId contextId, String beanName) {
		BeanStorage beanStorage = beanContextStorage.getBeanStorage(contextId);
		return beanStorage.getBean(beanName);
	}

	@Override
	public Map<String, Object> getBeans(BeanContextId contextId) {
		BeanStorage beanStorage = beanContextStorage.getBeanStorage(contextId);
		return beanStorage.getBeans();
	}
}
