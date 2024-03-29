package pl.subtelny.utilities.location;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import pl.subtelny.utilities.AlgorithmsUtil;

import java.util.Optional;
import java.util.stream.IntStream;

public final class LocationUtil {

    public static boolean isSameLocationPrecisionToBlock(Location loc1, Location loc2) {
        if (loc1.getWorld().equals(loc2.getWorld())) {
            if (loc1.getBlockX() == loc2.getBlockX()) {
                if (loc1.getBlockY() == loc2.getBlockY()) {
                    return loc1.getBlockZ() == loc2.getBlockZ();
                }
            }
        }
        return false;
    }

    public static Location toLocationWithCenteredBlock(Location location) {
        Location clone = location.clone();
        clone.setX(clone.getBlockX() + 0.5f);
        clone.setZ(clone.getBlockZ() + 0.5f);
        return clone;
    }

    public static Optional<Location> findSafeLocationSpirally(Location startFrom, int maxX, int maxY, int maxZ, int minX, int minY, int minZ) {
        int blockX = startFrom.getBlockX();
        int blockZ = startFrom.getBlockZ();
        if (blockX > maxX || blockX < minX || blockZ > maxZ || blockZ < minZ) {
            throw new IllegalArgumentException(String.format("Location not bound between maxX = %s, minX = %s, maxY = %s, minY = %s", maxX, minX, maxY, minY));
        }
        Integer[] next = new Integer[]{blockX, blockZ};
        while (true) {
            int x = next[0];
            int z = next[1];
            if (x > maxX || x < minX || z > maxZ || z < minZ) {
                break;
            }
            startFrom.setX(x);
            startFrom.setZ(z);

            Optional<Location> location = findSafeLocationBasedOnHigh(startFrom, minY, maxY);
            if (location.isPresent()) {
                return location;
            }
            next = AlgorithmsUtil.nextCoordsSpirally(blockX, blockZ);
        }
        return Optional.empty();
    }

    public static Optional<Location> findSafeLocationBasedOnHigh(Location location, int minY, int maxY) {
        return IntStream.rangeClosed(minY, maxY)
                .mapToObj(i -> {
                    location.setY(maxY);
                    return location;
                })
                .filter(LocationUtil::isSafeForPlayer)
                .findAny();
    }

    public static boolean isSafeForPlayer(Location location) {
        location.add(0, -1, 0);
        for (int i = 0; i <= 2; i++) {
            location.add(0, i, 0);
            Block block = location.getBlock();

            if (i == 0) {
                if (!block.getType().isSolid()) {
                    return false;
                }
            } else {
                if (block.getType() != Material.AIR) {
                    return false;
                }
            }
        }
        return true;
    }

}
