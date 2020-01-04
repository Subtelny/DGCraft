package pl.subtelny.core.api;

import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;

@Component
public final class CoreAPI {

	private static CoreAPI instance;

	private final Accounts accounts;

	private final AccountService accountService;

	@Autowired
	public CoreAPI(Accounts accounts, AccountService accountService) {
		this.accounts = accounts;
		this.accountService = accountService;
		instance = this;
	}

	public static AccountService getAccountService() {
		return instance.accountService;
	}

	public static Accounts getAccounts() {
		return instance.accounts;
	}


}
