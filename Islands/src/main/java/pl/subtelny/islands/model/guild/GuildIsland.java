package pl.subtelny.islands.model.guild;

import com.google.common.collect.Sets;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import org.bukkit.entity.Player;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandMember;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.utils.cuboid.Cuboid;
import pl.subtelny.validation.ValidationException;

public class GuildIsland extends Island {

	private Guild owner;

	public GuildIsland(IslandId islandId, Cuboid cuboid, LocalDate createdDate) {
		super(islandId, cuboid, createdDate);
	}

	@Override
	public void recalculateSpawn() {
		//TODO
	}

	@Override
	public boolean isInIsland(Player player) {
		return owner.getMembers()
				.stream().anyMatch(islander -> islander.getAccount().getId().equals(AccountId.of(player.getUniqueId())));
	}

	@Override
	public Optional<IslandMember> getOwner() {
		return Optional.ofNullable(owner);
	}

	@Override
	public Set<IslandMember> getMembers() {
		Set<IslandMember> islandMembers = Sets.newHashSet();
		if (owner != null) {
			islandMembers.addAll(owner.getMembers());
		}
		return islandMembers;
	}

	@Override
	public void changeOwner(IslandMember newOwner) {
		if (!(newOwner instanceof Guild)) {
			throw new ValidationException("Owner of this island have to be a guild");
		}
		this.owner = (Guild) newOwner;
	}

	@Override
	public void addMember(IslandMember member) {
		throw new ValidationException("You cant add member directly into guild island. Add member into guild");
	}

	@Override
	public void removeMember(IslandMember member) {
		throw new ValidationException("You cant remove member directly from guild island. Remove member from guild");
	}

	@Override
	public IslandType getIslandType() {
		return IslandType.GUILD;
	}

}
