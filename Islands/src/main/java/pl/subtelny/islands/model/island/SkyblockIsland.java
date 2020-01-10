package pl.subtelny.islands.model.island;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.*;
import pl.subtelny.islands.repository.loader.island.IslandAnemia;
import pl.subtelny.islands.repository.loader.island.SkyblockIslandAnemia;
import pl.subtelny.islands.settings.Settings;
import pl.subtelny.islands.utils.LocationUtil;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import pl.subtelny.utils.cuboid.Cuboid;
import pl.subtelny.validation.ValidationException;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SkyblockIsland extends Island {

    private final IslandCoordinates islandCoordinates;

    private Islander owner;

    private Set<Islander> members = Sets.newConcurrentHashSet();

    public SkyblockIsland(IslandAnemia islandAnemia, IslandCoordinates islandCoordinates, Cuboid cuboid) {
        super(islandAnemia, cuboid);
        Preconditions.checkArgument(islandAnemia.getIslandType() == IslandType.SKYBLOCK,
                String.format("This IslandAnemia {%s} cannot be add to SkyblockIsland", islandAnemia));
        this.islandCoordinates = islandCoordinates;
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
        getIslandAnemia().setExtendLevel(extendLevel);
        this.cuboid = SkyblockIslandUtil.calculateIslandCuboid(Settings.SkyblockIsland.ISLAND_WORLD, x, z, sumSize, space);
    }

    @Override
    public boolean isInIsland(Player player) {
        Set<Islander> members = Sets.newHashSet(this.members);
        members.add(owner);
        AccountId accountId = AccountId.of(player.getUniqueId());
        return members.stream().anyMatch(islander -> islander.getAccount().getAccountId().equals(accountId));
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
        validateIslandMemberType(newOwner);
        Islander newOwnerIslander = (Islander) newOwner;
        Optional<SkyblockIsland> newOwnerIsland = newOwnerIslander.getIsland();
        if (newOwnerIsland.isPresent()) {
            throw new ValidationException("Cannot change owners. New owner has own island");
        }
        this.owner = newOwnerIslander;
        newOwnerIslander.setIsland(this);
    }

    @Override
    public void addMember(IslandMember member) {
        validateIslandMemberType(member);
        Islander islander = (Islander) member;
        Optional<SkyblockIsland> islanderIsland = islander.getIsland();
        if (islanderIsland.isPresent()) {
            throw new ValidationException("IslandMember already has an island");
        }
        members.add(islander);
        islander.setIsland(this);
    }

    @Override
    public void removeMember(IslandMember member) {
        validateIslandMemberType(member);
        Islander islander = (Islander) member;
        if (members.contains(islander)) {
            members.remove(islander);
            islander.setIsland(null);
            return;
        }
        String message = String.format("Islander {Id: %s} is not added to island {Id: %s}", islander.getAccount().getAccountId(), getIslandId());
        throw new ValidationException(message);
    }

    private void validateIslandMemberType(IslandMember member) {
        if (member.getIslandMemberType() != IslandMemberType.ISLANDER) {
            throw new ValidationException("SkyblockIsland accepts only Islanders as members");
        }
    }

    public int getExtendLevel() {
        return getIslandAnemia().getExtendLevel();
    }

    public IslandCoordinates getIslandCoordinates() {
        return islandCoordinates;
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

    @Override
    public SkyblockIslandAnemia getIslandAnemia() {
        return (SkyblockIslandAnemia) super.getIslandAnemia();
    }
}
