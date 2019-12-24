package pl.subtelny.core.model;

import org.apache.commons.lang.Validate;
import pl.subtelny.identity.BasicIdentity;

import java.time.LocalDate;

public class Account extends BasicIdentity<AccountId> {

    private String nickname;

    private LocalDate lastOnline;

    public Account(AccountId id) {
        super(id);
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDate getLastOnline() {
        return lastOnline;
    }

    public void setNickname(String nickname) {
        Validate.notNull(nickname, "Nickname cannot be null");
        Validate.isTrue(nickname.length() > 0, "Nickname length is zero");
        this.nickname = nickname;
    }

    public void setLastOnline(LocalDate lastOnline) {
        Validate.notNull(lastOnline, "LocalDate cannot be null");
        this.lastOnline = lastOnline;
    }
}
