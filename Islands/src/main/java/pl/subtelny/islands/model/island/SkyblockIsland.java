package pl.subtelny.islands.model.island;

import com.google.common.collect.Sets;
import java.util.Optional;
import java.util.Set;
import org.bukkit.Location;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.repository.island.anemia.SkyblockIslandAnemia;
import pl.subtelny.islands.settings.Settings;
import pl.subtelny.islands.utils.LocationUtil;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import pl.subtelny.utils.cuboid.Cuboid;
import pl.subtelny.validation.ValidationException;

public class SkyblockIsland extends Island {

	private Set<AccountId> members = Sets.newConcurrentHashSet();

	public SkyblockIsland(SkyblockIslandAnemia islandAnemia, Cuboid cuboid, Set<AccountId> members) {
		super(islandAnemia, cuboid);
		this.members = members;
	}

	public SkyblockIsland(SkyblockIslandAnemia islandAnemia, Cuboid cuboid) {
		super(islandAnemia, cuboid);
	}

	public void extendCuboid(int extendLevel) {
		if (extendLevel > Settings.SkyblockIsland.EXTEND_LEVELS || extendLevel <= 0) {
			throw new ValidationException(String.format("Value not match in bound %s-%s", 0, Settings.SkyblockIsland.EXTEND_LEVELS));
		}
		IslandCoordinates islandCoordinates = getIslandAnemia().getIslandCoordinates();
		int x = islandCoordinates.getX();
		int z = islandCoordinates.getZ();
		int defaultSize = Settings.SkyblockIsland.ISLAND_SIZE;
		int extendSize = Settings.SkyblockIsland.EXTEND_SIZE_PER_LEVEL * extendLevel;
		int sumSize = defaultSize + extendSize;
		int space = Settings.SkyblockIsland.SPACE_BETWEEN_ISLANDS;
		if (extendLevel < Settings.SkyblockIsland.EXTEND_LEVELS) {
			space = 0;
		}
		getIslandAnemia().setExtendLevel(extendLevel);
		this.cuboid = SkyblockIslandUtil.calculateIslandCuboid(Settings.SkyblockIsland.ISLAND_WORLD, x, z, sumSize, space);
	}

	@Override
	public void recalculateSpawn() {
		Location center = getCuboid().getCenter();

		int maxY = (int) getCuboid().getUpperY();
		int maxX = (int) getCuboid().getUpperX();
		int maxZ = (int) getCuboid().getUpperZ();
		int minX = (int) getCuboid().getLowerX();
		int minY = (int) getCuboid().getLowerY();
		int minZ = (int) getCuboid().getLowerZ();

		Optional<Location> safe = LocationUtil.findSafeLocationSpirally(center, maxX, maxY, maxZ, minX, minY, minZ);
		safe.ifPresent(this::changeSpawn);
	}

	public boolean isInIsland(Islander islander) {
		AccountId accountId = islander.getAccount().getAccountId();
		return getMembersAndOwner().contains(accountId);
	}

	public void changeOwner(Islander newOwner) {
		Optional<SkyblockIsland> newOwnerIsland = newOwner.getIsland();
		if (newOwnerIsland.isPresent()) {
			throw new ValidationException("Cannot change owners. New owner has own island");
		}
		SkyblockIslandAnemia islandAnemia = getIslandAnemia();
		AccountId accountId = newOwner.getAccount().getAccountId();
		islandAnemia.setOwner(accountId);
		newOwner.setIsland(this);
	}

	public void addMember(Islander islander) {
		Optional<SkyblockIsland> islanderIsland = islander.getIsland();
		if (islanderIsland.isPresent()) {
			throw new ValidationException("IslandMember already has an island");
		}
		AccountId accountId = islander.getAccount().getAccountId();
		members.add(accountId);
		islander.setIsland(this);
	}

	public void removeMember(Islander islander) {
		AccountId accountId = islander.getAccount().getAccountId();
		if (members.contains(accountId)) {
			members.remove(accountId);
			islander.setIsland(null);
			return;
		}
		String message = String.format("Islander {Id: %s} is not added to island {Id: %s}", accountId, getIslandId());
		throw new ValidationException(message);
	}

	public AccountId getOwner() {
		return getIslandAnemia().getOwner();
	}

	public Set<AccountId> getMembers() {
		return Sets.newHashSet(members);
	}

	public Set<AccountId> getMembersAndOwner() {
		Set<AccountId> members = getMembers();
		members.add(getIslandAnemia().getOwner());
		return members;
	}

	public int getExtendLevel() {
		return getIslandAnemia().getExtendLevel();
	}

	public IslandCoordinates getIslandCoordinates() {
		return getIslandAnemia().getIslandCoordinates();
	}

	@Override
	public IslandType getIslandType() {
		return IslandType.SKYBLOCK;
	}

	@Override
	protected SkyblockIslandAnemia getIslandAnemia() {
		return (SkyblockIslandAnemia) super.getIslandAnemia();
	}
}
