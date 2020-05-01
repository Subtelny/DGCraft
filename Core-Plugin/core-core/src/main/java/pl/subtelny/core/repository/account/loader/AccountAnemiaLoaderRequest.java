package pl.subtelny.core.repository.account.loader;

import com.google.common.collect.Lists;
import pl.subtelny.core.api.account.AccountId;
import org.jooq.Condition;
import pl.subtelny.generated.tables.tables.Accounts;

import java.util.List;
import java.util.Optional;

public class AccountAnemiaLoaderRequest {

    private final List<Condition> where;

    public AccountAnemiaLoaderRequest(List<Condition> where) {
        this.where = where;
    }

    public List<Condition> getWhere() {
        return where;
    }

    public static AccountAnemiaLoaderRequest toRequest(AccountLoadRequest request) {
        Builder builder = new Builder();
        Optional<AccountId> accountIdOpt = request.getAccountId();
        Optional<String> nameOpt = request.getName();

        if (accountIdOpt.isPresent()) {
            AccountId accountId = accountIdOpt.get();
            builder.where(accountId);
        }
        if (nameOpt.isPresent()) {
            String name = nameOpt.get();
            builder.where(name);
        }
        return builder.build();
    }


    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private List<Condition> where = Lists.newArrayList();

        public Builder where(AccountId accountId) {
            Condition conditionId = Accounts.ACCOUNTS.ID.eq(accountId.getId());
            where.add(conditionId);
            return this;
        }

        public Builder where(String name) {
            Condition conditionName = Accounts.ACCOUNTS.NAME.eq(name);
            where.add(conditionName);
            return this;
        }

        public AccountAnemiaLoaderRequest build() {
            return new AccountAnemiaLoaderRequest(where);
        }

    }

}
