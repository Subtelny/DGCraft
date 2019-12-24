package pl.subtelny.islands.model;

import java.time.LocalDate;
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

	public int getExtendLevel() {
		return extendLevel;
	}

	public void extendIsland(int extendLevel) {
		int x = islandCoordinates.getX();
		int z = islandCoordinates.getZ();
		IslandUtil.calculateIslandCuboid(Settings.ISLAND_WORLD,x, z, Settings.ISLAND_SIZE, Settings.SPACE_BETWEEN_ISLANDS);
	}

	@Override
	void changeOwner(IslandMember owner) {
		if (owner instanceof Islander) {
			this.owner = owner;
		} else {
			throw new ValidationException("SkyblockIsland accepts only Islanders");
		}
	}

	@Override
	void addMember(IslandMember member) {
		if (member instanceof Islander) {
			members.add(member);
		} else {
			throw new ValidationException("SkyblockIsland accepts only Islanders");
		}
	}

	@Override
	void removeMember(IslandMember member) {
		if (member instanceof Islander) {
			((Islander) member).setIsland(null);
			members.remove(member);
		}
	}
}
