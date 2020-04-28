package pl.subtelny.core.repository.loginhistory.entity;

import pl.subtelny.utilities.BasicIdentity;

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
