package pl.subtelny.islands.model.guild;

import com.google.common.base.Preconditions;
import pl.subtelny.islands.model.island.Island;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.repository.island.anemia.IslandAnemia;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.Optional;

public class GuildIsland extends Island {

	private GuildId owner;

	public GuildIsland(IslandAnemia islandAnemia, Cuboid cuboid) {
		super(cuboid);
		Preconditions.checkArgument(islandAnemia.getIslandType() == IslandType.GUILD,
				String.format("This IslandAnemia {%s} cannot be add to GuildIsland", islandAnemia));
	}

	@Override
	public boolean isInIsland(Islander islander) {
		Optional<GuildId> ownerOpt = getOwner();
		if(ownerOpt.isPresent()) {
			Optional<GuildId> guildOpt = islander.getGuild();
			if (guildOpt.isPresent()) {
				return ownerOpt.get().equals(guildOpt.get());
			}
		}
		return false;
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
