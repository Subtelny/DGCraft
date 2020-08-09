package pl.subtelny.core.api.account;

import pl.subtelny.core.api.city.CityId;

import java.time.LocalDateTime;
import java.util.Optional;

public interface Account {

    void setDisplayName(String displayName);

    void setName(String name);

    void setLastOnline(LocalDateTime lastOnline);

    void setCityId(CityId cityId);

    Optional<CityId> getCityId();

    AccountId getAccountId();

    String getName();

    LocalDateTime getLastOnline();

    String getDisplayName();

}
