package pl.subtelny.islands.model.guild;

import com.google.common.collect.Sets;
import java.util.Set;

import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.model.islander.IslanderId;

public class Guild {

	private Set<IslanderId> members = Sets.newConcurrentHashSet();

	public boolean isInGuild(Islander islander) {
		IslanderId accountId = islander.getIslanderId();
		return members.contains(accountId);
	}

	public Set<IslanderId> getMembers() {
		return Sets.newHashSet(members);
	}

	public GuildId getGuildId() {
		return null;
	}
}
