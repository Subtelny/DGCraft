package pl.subtelny;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import pl.subtelny.beans.BeanContext;
import pl.subtelny.beans.BeanLoader;
import pl.subtelny.core.api.Accounts;
import pl.subtelny.core.api.CoreAPI;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;

public class Test {

	@org.junit.Test
	public void test() throws ExecutionException {
		String path = this.getClass().getPackage().getName() + ".core";
		BeanLoader beanLoader = new BeanLoader(path);
		beanLoader.initializeBeans();

		Accounts accounts = CoreAPI.getAccounts();
	}

}
