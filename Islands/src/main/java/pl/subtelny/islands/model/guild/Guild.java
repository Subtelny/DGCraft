package pl.subtelny.islands.model.guild;

import com.google.common.collect.Sets;
import java.util.Set;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.islander.Islander;

public class Guild {

	private Set<AccountId> members = Sets.newConcurrentHashSet();

	public boolean isInGuild(Islander islander) {
		AccountId accountId = islander.getAccount().getAccountId();
		return members.contains(accountId);
	}

	public Set<AccountId> getMembers() {
		return Sets.newHashSet(members);
	}

}
