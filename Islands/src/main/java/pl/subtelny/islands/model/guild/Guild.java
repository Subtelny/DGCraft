package pl.subtelny.islands.model.guild;

import com.google.common.collect.Sets;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import pl.subtelny.islands.model.IslandMember;
import pl.subtelny.islands.model.IslandMemberType;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.validation.ValidationException;

public class Guild implements IslandMember {

	private Set<Islander> members = Sets.newConcurrentHashSet();

	public boolean isInGuild(Islander islander) {
		return members.contains(islander);
	}

	public Set<Islander> getMembers() {
		return Sets.newHashSet(members);
	}

	@Override
	public IslandMemberType getIslandMemberType() {
		return IslandMemberType.GUILD;
	}
}
