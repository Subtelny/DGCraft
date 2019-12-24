package pl.subtelny.core.model;

import pl.subtelny.identity.BasicIdentity;

public class LoginHistoryId extends BasicIdentity<Integer> {

    public LoginHistoryId() {
        super();
    }

    public LoginHistoryId(Integer id) {
        super(id);
    }

    public static LoginHistoryId of(Integer id) {
        return new LoginHistoryId(id);
    }

}
