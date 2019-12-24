package pl.subtelny.core.model;

import pl.subtelny.identity.BasicIdentity;

import java.util.UUID;

public class AccountId extends BasicIdentity<UUID> {

    public AccountId() {
        super(id);
    }

    public AccountId(UUID id) {
        super(id);
    }

    public static AccountId of(UUID id) {
        return new AccountId(id);
    }

}
