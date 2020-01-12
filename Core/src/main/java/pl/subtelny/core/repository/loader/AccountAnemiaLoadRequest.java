package pl.subtelny.core.repository.loader;

import com.google.common.collect.Lists;
import org.jooq.Condition;
import pl.subtelny.core.generated.tables.Accounts;
import pl.subtelny.core.model.AccountId;

import java.util.List;

public class AccountAnemiaLoadRequest {

    private final List<Condition> where;

    public AccountAnemiaLoadRequest(List<Condition> where) {
        this.where = where;
    }

    public List<Condition> getWhere() {
        return where;
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

        public AccountAnemiaLoadRequest build() {
            return new AccountAnemiaLoadRequest(where);
        }

    }

}
