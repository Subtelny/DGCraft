package pl.subtelny.core.repository;

import org.jooq.Configuration;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.core.repository.loader.AccountAnemia;
import pl.subtelny.core.repository.loader.AccountAnemiaLoader;
import pl.subtelny.core.repository.loader.AccountAnemiaLoaderRequest;

import java.util.List;
import java.util.Optional;

@Component
public class AccountRepository {

    private final Configuration configuration;

    @Autowired
    public AccountRepository(Configuration configuration) {
        this.configuration = configuration;
    }

    public Optional<AccountAnemia> findAccount(AccountId accountId) {
        AccountAnemiaLoaderRequest request = AccountAnemiaLoaderRequest.newBuilder()
                .where(accountId)
                .build();
        return loadAccount(request);
    }

    private Optional<AccountAnemia> loadAccount(AccountAnemiaLoaderRequest request) {
        AccountAnemiaLoader loader = new AccountAnemiaLoader(configuration, request);
        List<AccountAnemia> loadedData = loader.perform().getLoadedData();
        if (loadedData.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(loadedData.get(0));
    }

    public void saveAccount(AccountAnemia account) {
        //TODO
        //to implement
    }

}
