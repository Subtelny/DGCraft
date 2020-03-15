package pl.subtelny.core.api;

import pl.subtelny.core.api.account.Accounts;

//@Component
public final class CoreAPI {

	private static CoreAPI instance;

	private final Accounts accounts;

	//@Autowired
	public CoreAPI(Accounts accounts) {
		this.accounts = accounts;
		instance = this;
	}

	public static Accounts getAccounts() {
		return instance.accounts;
	}


}
