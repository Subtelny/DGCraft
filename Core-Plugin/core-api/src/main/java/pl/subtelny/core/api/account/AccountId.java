package pl.subtelny.core.api.account;

import pl.subtelny.utilities.identity.BasicIdentity;

import java.util.UUID;

public class AccountId extends BasicIdentity<UUID> {

	public AccountId() {
	}

	public AccountId(UUID id) {
		super(id);
	}

	public static AccountId of(UUID id) {
		return new AccountId(id);
	}

}
