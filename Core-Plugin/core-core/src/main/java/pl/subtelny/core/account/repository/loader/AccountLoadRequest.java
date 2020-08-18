package pl.subtelny.core.account.repository.loader;

import java.util.Optional;

import pl.subtelny.core.api.account.AccountId;

public class AccountLoadRequest {

	private final AccountId accountId;

	private final String name;

	public AccountLoadRequest(AccountId accountId, String name) {
		this.accountId = accountId;
		this.name = name;
	}

	public Optional<AccountId> getAccountId() {
		return Optional.ofNullable(accountId);
	}

	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {

		private AccountId accountId;

		private String name;

		public Builder where(AccountId accountId) {
			this.accountId = accountId;
			return this;
		}

		public Builder where(String name) {
			this.name = name;
			return this;
		}

		public AccountLoadRequest build() {
			return new AccountLoadRequest(accountId, name);
		}

	}

}
