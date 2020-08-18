package pl.subtelny.core.loginhistory.repository.entity;

import pl.subtelny.utilities.identity.BasicIdentity;

public class LoginHistoryId extends BasicIdentity<Integer> {

    public LoginHistoryId() {
        super();
    }

    public LoginHistoryId(Integer id) {
        super(id);
    }

    public static LoginHistoryId empty() {
        return of(null);
    }

    public static LoginHistoryId of(Integer id) {
        return new LoginHistoryId(id);
    }

}
