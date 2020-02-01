package pl.subtelny.islands.model.guild;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import java.util.Optional;
import java.util.Set;
import org.bukkit.entity.Player;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.model.IslandMember;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.repository.island.anemia.GuildIslandAnemia;
import pl.subtelny.islands.repository.island.anemia.IslandAnemia;
import pl.subtelny.utils.cuboid.Cuboid;
import pl.subtelny.validation.ValidationException;

public class GuildIsland extends Island {

	private Guild owner;

	public GuildIsland(IslandAnemia islandAnemia, Cuboid cuboid) {
		super(cuboid);
		Preconditions.checkArgument(islandAnemia.getIslandType() == IslandType.GUILD,
				String.format("This IslandAnemia {%s} cannot be add to GuildIsland", islandAnemia));
	}

	@Override
	public void recalculateSpawn() {
		//TODO
	}

	@Override
	public boolean isInIsland(Player player) {
		return owner.getMembers()
				.stream().anyMatch(islander -> islander.getAccount().getAccountAnemia().getAccountId().equals(AccountId.of(player.getUniqueId())));
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

	@Override
	public GuildIslandAnemia getIslandAnemia() {
		return (GuildIslandAnemia) super.getIslandAnemia();
	}
}
