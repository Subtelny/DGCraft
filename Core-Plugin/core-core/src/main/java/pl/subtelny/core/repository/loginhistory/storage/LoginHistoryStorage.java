package pl.subtelny.core.repository.loginhistory.storage;

import com.github.benmanes.caffeine.cache.Caffeine;
import pl.subtelny.core.api.loginhistory.LoginHistory;
import pl.subtelny.repository.Storage;

import java.util.List;

public class LoginHistoryStorage extends Storage<LoginHistoryCacheKey, List<LoginHistory>> {

    public LoginHistoryStorage() {
        super(Caffeine.newBuilder().build());
    }

}
