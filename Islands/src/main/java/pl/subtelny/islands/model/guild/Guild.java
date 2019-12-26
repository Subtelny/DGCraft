package pl.subtelny.islands.model.guild;

import com.google.common.collect.Sets;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandMember;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.validation.ValidationException;

public class Guild implements IslandMember {

	private Island island;

	private Set<Islander> members = Sets.newConcurrentHashSet();

	public Optional<Island> getIsland() {
		return Optional.ofNullable(island);
	}

	public boolean isInGuild(Islander islander) {
		return members.contains(islander);
	}

	public void setIsland(@Nullable Island newIsland) {
		if(newIsland == null) {
			removeIsland();
			return;
		}
		setNewIsland(newIsland);
	}

	private void setNewIsland(Island newIsland) {
		Optional<IslandMember> ownerOpt = newIsland.getOwner();
		if(ownerOpt.isPresent() && ownerOpt.get().equals(this)) {
			this.island = newIsland;
		} else {
			throw new ValidationException("Cannot set new island. Guild is not owner of this island");
		}
	}

	private void removeIsland() {
		if(island != null) {
			Optional<IslandMember> ownerOpt = island.getOwner();
			if(ownerOpt.isPresent()) {
				if(ownerOpt.get().equals(this)) {
					throw new ValidationException("Cannot remove island from guild. Guild still exists in island as owner");
				}
			}
			island = null;
		}
	}

	public Set<Islander> getMembers() {
		return Sets.newHashSet(members);
	}
}
