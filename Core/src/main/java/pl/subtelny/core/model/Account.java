package pl.subtelny.core.model;

import org.apache.commons.lang.Validate;
import pl.subtelny.identity.BasicIdentity;

import java.time.LocalDate;

public class Account extends BasicIdentity<AccountId> {

    private String name;

    private String displayName;

    private LocalDate lastOnline;

    public Account(AccountId id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public LocalDate getLastOnline() {
        return lastOnline;
    }

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		Validate.notNull(name, "DisplayName cannot be null");
		Validate.isTrue(name.length() > 0, "DisplayName length is zero");
		this.displayName = displayName;
	}

	public void setName(String name) {
        Validate.notNull(name, "Name cannot be null");
        Validate.isTrue(name.length() > 0, "Name length is zero");
        this.name = name;
    }

    public void setLastOnline(LocalDate lastOnline) {
        Validate.notNull(lastOnline, "LocalDate cannot be null");
        this.lastOnline = lastOnline;
    }

}
