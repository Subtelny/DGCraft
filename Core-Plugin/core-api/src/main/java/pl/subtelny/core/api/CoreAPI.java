package pl.subtelny.core.api;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.core.api.account.Accounts;

//@Component
public final class CoreAPI {

	public static final String PLUGIN_NAME = "DG-Core";

	private static CoreAPI instance;

	private final Accounts accounts;

	@Autowired
	public CoreAPI(Accounts accounts) {
		this.accounts = accounts;
		instance = this;
	}

	public static Accounts getAccounts() {
		return instance.accounts;
	}


}
