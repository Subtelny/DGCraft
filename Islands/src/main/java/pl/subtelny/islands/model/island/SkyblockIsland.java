package pl.subtelny.islands.model.island;

import com.google.common.collect.Sets;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandMember;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.settings.Settings;
import pl.subtelny.islands.utils.LocationUtil;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import pl.subtelny.utils.cuboid.Cuboid;
import pl.subtelny.validation.ValidationException;

public class SkyblockIsland extends Island {

	private Islander owner;

	private Set<Islander> members = Sets.newConcurrentHashSet();

	private final IslandCoordinates islandCoordinates;

	private int extendLevel;

	protected SkyblockIsland(IslandId islandId, IslandCoordinates islandCoordinates, Cuboid cuboid, LocalDate createdDate) {
		super(islandId, cuboid, createdDate);
		this.islandCoordinates = islandCoordinates;
	}

	@Override
	public boolean isInIsland(Player player) {
		AccountId accountId = AccountId.of(player.getUniqueId());
		if (owner.getAccount().getId().equals(accountId)) {
			return true;
		}
		return members.stream().anyMatch(islander -> islander.getAccount().getId().equals(AccountId.of(player.getUniqueId())));
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

	@Override
	public void changeOwner(IslandMember newOwner) {
		if (!(newOwner instanceof Islander)) {
			throw new ValidationException("SkyblockIsland accepts only Islander as owner");
		}
		Islander newOwnerIslander = (Islander) newOwner;
		Optional<Island> newOwnerIsland = newOwnerIslander.getIsland();
		if (newOwnerIsland.isPresent()) {
			throw new ValidationException("Cannot change owners. New owner has own island");
		}
		this.owner = newOwnerIslander;
		newOwnerIslander.setIsland(this);
	}

	@Override
	public void addMember(IslandMember member) {
		if (!(member instanceof Islander)) {
			throw new ValidationException("SkyblockIsland accepts only Islanders as members");
		}
		Islander islander = (Islander) member;
		Optional<Island> islanderIsland = islander.getIsland();
		if (islanderIsland.isPresent()) {
			throw new ValidationException("IslandMember already has a island");
		}
		members.add(islander);
		islander.setIsland(this);
	}

	@Override
	public void removeMember(IslandMember member) {
		if (!(member instanceof Islander)) {
			throw new ValidationException("SkyblockIsland accepts only Islanders as members");
		}
		Islander islander = (Islander) member;
		Optional<Island> islanderIslandOpt = islander.getIsland();
		if (islanderIslandOpt.isEmpty() || !islanderIslandOpt.get().equals(this)) {
			throw new ValidationException("This Islander is not added to this island");
		}
		members.remove(islander);
		islander.setIsland(null);
	}

	public void extendCuboid(int extendLevel) {
		if (extendLevel > Settings.SkyblockIsland.EXTEND_LEVELS || extendLevel <= 0) {
			throw new ValidationException(String.format("Value not match in bound %s-%s", 0, Settings.SkyblockIsland.EXTEND_LEVELS));
		}
		int x = islandCoordinates.getX();
		int z = islandCoordinates.getZ();
		int defaultSize = Settings.SkyblockIsland.ISLAND_SIZE;
		int extendSize = Settings.SkyblockIsland.EXTEND_SIZE_PER_LEVEL * extendLevel;
		int sumSize = defaultSize + extendSize;
		int space = Settings.SkyblockIsland.SPACE_BETWEEN_ISLANDS;
		if (extendLevel < Settings.SkyblockIsland.EXTEND_LEVELS) {
			space = 0;
		}
		this.extendLevel = extendLevel;
		this.cuboid = SkyblockIslandUtil.calculateIslandCuboid(Settings.SkyblockIsland.ISLAND_WORLD, x, z, sumSize, space);
	}

	@Override
	public IslandType getIslandType() {
		return IslandType.SKYBLOCK;
	}

	@Override
	public Optional<IslandMember> getOwner() {
		return Optional.ofNullable(owner);
	}

	@Override
	public Set<IslandMember> getMembers() {
		return Sets.newHashSet(members);
	}

	public int getExtendLevel() {
		return extendLevel;
	}

	public IslandCoordinates getIslandCoordinates() {
		return islandCoordinates;
	}
}
