package pl.subtelny.islands.model.island;

import java.time.LocalDate;
import java.util.Optional;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandMember;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.settings.Settings;
import pl.subtelny.islands.utils.IslandUtil;
import pl.subtelny.utils.cuboid.Cuboid;
import pl.subtelny.validation.ValidationException;

public class SkyblockIsland extends Island {

	private final IslandCoordinates islandCoordinates;

	private int extendLevel;

	protected SkyblockIsland(IslandId islandId, IslandCoordinates islandCoordinates, Cuboid cuboid, LocalDate createdDate) {
		super(islandId, cuboid, createdDate);
		this.islandCoordinates = islandCoordinates;
	}

	public void extendCuboid(int extendLevel) {
		if (extendLevel > Settings.EXTEND_LEVELS || extendLevel <= 0) {
			throw new ValidationException(String.format("Value not match in bound %s-%s", 0, Settings.EXTEND_LEVELS));
		}
		int x = islandCoordinates.getX();
		int z = islandCoordinates.getZ();
		int defaultSize = Settings.ISLAND_SIZE;
		int extendSize = Settings.EXTEND_SIZE_PER_LEVEL * extendLevel;
		int sumSize = defaultSize + extendSize;
		int space = Settings.SPACE_BETWEEN_ISLANDS;
		if (extendLevel < Settings.EXTEND_LEVELS) {
			space = 0;
		}
		this.extendLevel = extendLevel;
		this.cuboid = IslandUtil.calculateIslandCuboid(Settings.ISLAND_WORLD, x, z, sumSize, space);
	}

	@Override
	public void changeOwner(IslandMember newOwner) {
		if (newOwner instanceof Islander) {
			Optional<Island> newOwnerIsland = ((Islander) newOwner).getIsland();
			if (newOwnerIsland.isPresent()) {
				throw new ValidationException("Cannot change owners, new owner has own island");
			}
			this.owner = newOwner;
		} else {
			throw new ValidationException("SkyblockIsland accepts only Islander as owner");
		}
	}

	@Override
	public void addMember(IslandMember member) {
		if (member instanceof Islander) {
			Islander islander = (Islander) member;
			Optional<Island> islanderIsland = islander.getIsland();
			if (islanderIsland.isPresent()) {
				throw new ValidationException("IslandMember already has a island");
			}
			members.add(member);
			islander.setIsland(this);
		} else {
			throw new ValidationException("SkyblockIsland accepts only Islanders as members");
		}
	}

	@Override
	public void removeMember(IslandMember member) {
		if (member instanceof Islander) {
			((Islander) member).setIsland(null);
			members.remove(member);
		}
	}

	@Override
	public IslandType getIslandType() {
		return IslandType.SKYBLOCK;
	}

	public int getExtendLevel() {
		return extendLevel;
	}
}
