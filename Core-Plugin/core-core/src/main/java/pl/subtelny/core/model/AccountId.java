package pl.subtelny.core.model;

import pl.subtelny.utilities.BasicIdentity;

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
