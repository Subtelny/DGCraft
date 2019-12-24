package pl.subtelny.islands.model.guild;

import java.time.LocalDate;
import java.util.Optional;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandMember;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.utils.cuboid.Cuboid;
import pl.subtelny.validation.ValidationException;

public class GuildIsland extends Island {

	public GuildIsland(IslandId islandId, Cuboid cuboid, LocalDate createdDate) {
		super(islandId, cuboid, createdDate);
	}

	@Override
	public void changeOwner(IslandMember newOwner) {
		if (newOwner instanceof Guild) {
			Guild newOwnerGuild = (Guild) newOwner;
			if(newOwnerGuild.getIsland().isPresent()) {
				throw new ValidationException("Cannot change owners, new owner has own island");
			}

			newOwnerGuild.set
			this.owner = newOwnerGuild;
		} else {
			throw new ValidationException("GuildIsland accepts only Guild as owner");
		}
	}

	@Override
	public void addMember(IslandMember member) {
		if (member instanceof Islander) {
			Optional<Guild> guildOpt = ((Islander) member).getGuild();
			if (guildOpt.isPresent()) {
				throw new ValidationException("That member already has a guild");
			}
			members.add(member);
		} else {
			throw new ValidationException("GuildIsland accepts only Islanders as members");
		}
	}

	@Override
	public void removeMember(IslandMember member) {

	}

	@Override
	public IslandType getIslandType() {
		return IslandType.GUILD;
	}

	public Guild getGuild() {
		return guild;
	}

}
