package pl.subtelny.islands.model.guild;

import com.google.common.base.Preconditions;
import pl.subtelny.islands.islander.model.Island;
import pl.subtelny.islands.islander.model.IslandType;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.island.repository.anemia.IslandAnemia;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.Optional;

public class GuildIsland extends Island {

	private GuildId owner;

	public GuildIsland(IslandAnemia islandAnemia, Cuboid cuboid) {
		super(islandAnemia.getIslandId(), cuboid);
		Preconditions.checkArgument(islandAnemia.getIslandType() == IslandType.GUILD,
				String.format("This IslandAnemia {%s} cannot be add to GuildIsland", islandAnemia));
	}

	@Override
	public boolean isInIsland(Islander islander) {
		return false;
	}

	@Override
	public void addMember(Islander islander) {

	}

	@Override
	public void removeMember(Islander islander) {

	}

	@Override
	public void recalculateSpawn() {
		//TODO
	}

	public Optional<GuildId> getOwner() {
		return Optional.ofNullable(owner);
	}

	@Override
	public IslandType getIslandType() {
		return IslandType.GUILD;
	}

}
