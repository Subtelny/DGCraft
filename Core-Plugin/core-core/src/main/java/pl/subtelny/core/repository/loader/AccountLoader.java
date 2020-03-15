package pl.subtelny.core.repository.loader;

import org.jooq.Configuration;
import pl.subtelny.core.repository.AccountAnemia;
import pl.subtelny.core.repository.entity.AccountEntity;

import java.util.Optional;

public class AccountLoader {

    private final Configuration configuration;

    public AccountLoader(Configuration configuration) {
        this.configuration = configuration;
    }

    public Optional<AccountEntity> loadAccount(AccountLoadRequest request) {
        Optional<AccountAnemia> accountAnemia = performAction(request);
        if (accountAnemia.isPresent()) {
            AccountEntity account = AccountMapper.map(accountAnemia.get());
            return Optional.of(account);
        }
        return Optional.empty();
    }

    private Optional<AccountAnemia> performAction(AccountLoadRequest request) {
        AccountAnemiaLoadAction loader = new AccountAnemiaLoadAction(configuration, request);
        AccountAnemia loadedData = loader.perform();
        return Optional.ofNullable(loadedData);
    }

    private static class AccountMapper {

        private static AccountEntity map(AccountAnemia accountAnemia) {
			return new AccountEntity(
					accountAnemia.getAccountId(),
					accountAnemia.getName(),
					accountAnemia.getDisplayName(),
					accountAnemia.getLastOnline()
			);
        }

    }

}
