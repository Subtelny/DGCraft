package pl.subtelny.islands.api.flags;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class IslandFlags {

    public static final IslandFlag SPAWN_AGGRESSIVE_MOB = IslandFlag.of("SPAWN_AGGRESSIVE_MOB", true);
    public static final IslandFlag SPAWN_PASSIVE_MOB = IslandFlag.of("SPAWN_PASSIVE_MOB", true);
    public static final IslandFlag MOB_SPAWNERS = IslandFlag.of("MOB_SPAWNERS", true);
    public static final IslandFlag PVP = IslandFlag.of("PVP", false);
    public static final IslandFlag GUEST_OPEN = IslandFlag.of("GUEST_OPEN", false);
    public static final IslandFlag GUEST_SWITCH = IslandFlag.of("GUEST_SWITCH", false);
    public static final IslandFlag GUEST_PRESSURE = IslandFlag.of("GUEST_PRESSURE", false);
    public static final IslandFlag GUEST_ENTER = IslandFlag.of("GUEST_ENTER", true);
    public static final IslandFlag GUEST_DROP_ITEM = IslandFlag.of("GUEST_DROP_ITEM", false);
    public static final IslandFlag GUEST_PICKUP_ITEM = IslandFlag.of("GUEST_PICKUP_ITEM", false);
    public static final IslandFlag GUEST_FLY = IslandFlag.of("GUEST_FLY", true);
    public static final IslandFlag GUEST_LEASH_ENTITY = IslandFlag.of("GUEST_LEASH_ENTITY", false);
    public static final IslandFlag GUEST_ATTACK_PASSIVE_MOBS = IslandFlag.of("GUEST_ATTACK_PASSIVE_MOBS", false);
    public static final IslandFlag GUEST_ATTACK_AGGRESSIVE_MOBS = IslandFlag.of("GUEST_ATTACK_AGGRESSIVE_MOBS", false);
    public static final IslandFlag GUEST_MOUNT = IslandFlag.of("GUEST_MOUNT", false);
    public static final IslandFlag BREED = IslandFlag.of("BREED", true);
    public static final IslandFlag EXPLODE = IslandFlag.of("EXPLODE", true);

    public static Optional<IslandFlag> findIslandFlag(String key) {
        return getAllFlags().stream()
                .filter(islandFlag -> islandFlag.getKey().equals(key))
                .findFirst();
    }

    public static List<IslandFlag> getAllFlags() {
        return Arrays.asList(
                SPAWN_AGGRESSIVE_MOB,
                SPAWN_PASSIVE_MOB,
                MOB_SPAWNERS,
                PVP,
                GUEST_OPEN,
                GUEST_SWITCH,
                GUEST_PRESSURE,
                GUEST_ENTER,
                GUEST_DROP_ITEM,
                GUEST_PICKUP_ITEM,
                GUEST_FLY,
                GUEST_LEASH_ENTITY,
                GUEST_ATTACK_PASSIVE_MOBS,
                GUEST_ATTACK_AGGRESSIVE_MOBS,
                GUEST_MOUNT,
                BREED,
                EXPLODE
        );
    }

}
