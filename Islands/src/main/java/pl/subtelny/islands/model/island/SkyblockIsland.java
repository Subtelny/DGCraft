package pl.subtelny.islands.model.island;

import com.google.common.collect.Sets;
import org.bukkit.Location;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.settings.Settings;
import pl.subtelny.islands.utils.LocationUtil;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import pl.subtelny.utils.cuboid.Cuboid;
import pl.subtelny.validation.ValidationException;

import java.util.Optional;
import java.util.Set;

public class SkyblockIsland extends Island {

    private Set<AccountId> members = Sets.newConcurrentHashSet();

    private IslandCoordinates islandCoordinates;

    private AccountId owner;

    private int extendLevel;

    private int points;

    public SkyblockIsland(Cuboid cuboid, Set<AccountId> members, IslandCoordinates islandCoordinates, AccountId owner, int extendLevel, int points) {
        super(cuboid);
        this.members = members;
        this.islandCoordinates = islandCoordinates;
        this.owner = owner;
        this.extendLevel = extendLevel;
        this.points = points;
    }

    public SkyblockIsland(Cuboid cuboid) {
        super(cuboid);
    }

    public void extendCuboid(int extendLevel) {
        if (extendLevel > Settings.SkyblockIsland.EXTEND_LEVELS || extendLevel <= 0) {
            throw new ValidationException(String.format("Value not match in bound %s-%s", 0, Settings.SkyblockIsland.EXTEND_LEVELS));
        }
        IslandCoordinates islandCoordinates = getIslandCoordinates();
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
        AccountId accountId = islander.getAccount();
        return getMembersAndOwner().contains(accountId);
    }

    public void changeOwner(Islander newOwner) {
        Optional<IslandId> newOwnerIsland = newOwner.getSkyblockIsland();
        if (newOwnerIsland.isPresent()) {
            throw new ValidationException("Cannot change owners. New owner has own island");
        }
        this.owner = newOwner.getAccount();
        newOwner.setIsland(this);
    }

    public void addMember(Islander islander) {
        Optional<IslandId> islanderIsland = islander.getSkyblockIsland();
        if (islanderIsland.isPresent()) {
            throw new ValidationException("IslandMember already has an island");
        }
        AccountId accountId = islander.getAccount();
        members.add(accountId);
        islander.setIsland(this);
    }

    public void removeMember(Islander islander) {
        AccountId accountId = islander.getAccount();
        if (members.contains(accountId)) {
            members.remove(accountId);
            islander.setIsland(null);
            return;
        }
        String message = String.format("Islander {Id: %s} is not added to island {Id: %s}", accountId, getIslandId());
        throw new ValidationException(message);
    }

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public AccountId getOwner() {
        return owner;
    }

    public Set<AccountId> getMembers() {
        return Sets.newHashSet(members);
    }

    public Set<AccountId> getMembersAndOwner() {
        Set<AccountId> members = getMembers();
        members.add(owner);
        return members;
    }

    public int getExtendLevel() {
        return extendLevel;
    }

    public IslandCoordinates getIslandCoordinates() {
        return islandCoordinates;
    }

    @Override
    public IslandType getIslandType() {
        return IslandType.SKYBLOCK;
    }

}
