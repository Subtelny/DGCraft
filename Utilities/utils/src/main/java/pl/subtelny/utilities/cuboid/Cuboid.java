package pl.subtelny.utilities.cuboid;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

public class Cuboid implements Cloneable, ConfigurationSerializable, Iterable<Block> {

    protected int priority;
    protected String cuboidName;
    protected String worldName;
    protected final Vector minimumPoint, maximumPoint;

    public Cuboid(Cuboid cuboid) {
        this(cuboid.cuboidName, cuboid.worldName, cuboid.priority, cuboid.minimumPoint.getX(), cuboid.minimumPoint.getY(), cuboid.minimumPoint.getZ(), cuboid.maximumPoint.getX(), cuboid.maximumPoint.getY(), cuboid.maximumPoint.getZ());
    }

    public Cuboid(String cuboidName, Location loc) {
        this(cuboidName, loc, loc);
    }

    public Cuboid(String cuboidName, Location loc1, Location loc2) {
        this(cuboidName, 0, loc1, loc2);
    }

    public Cuboid(String cuboidName, int priority, Location loc1, Location loc2) {
        if (loc1 != null && loc2 != null) {
            if (loc1.getWorld() != null && loc2.getWorld() != null) {
                if (!loc1.getWorld().getUID().equals(loc2.getWorld().getUID()))
                    throw new IllegalStateException("The 2 locations of the cuboid must be in the same world!");
            } else {
                throw new NullPointerException("One/both of the worlds is/are null!");
            }
            this.worldName = loc1.getWorld().getName();
            this.cuboidName = cuboidName;
            this.priority = priority;

            double xPos1 = Math.min(loc1.getX(), loc2.getBlockX());
            double yPos1 = Math.min(loc1.getY(), loc2.getBlockY());
            double zPos1 = Math.min(loc1.getZ(), loc2.getBlockZ());
            double xPos2 = Math.max(loc1.getX(), loc2.getBlockX());
            double yPos2 = Math.max(loc1.getY(), loc2.getBlockY());
            double zPos2 = Math.max(loc1.getZ(), loc2.getBlockZ());
            this.minimumPoint = new Vector(xPos1, yPos1, zPos1);
            this.maximumPoint = new Vector(xPos2, yPos2, zPos2);
        } else {
            throw new NullPointerException("One/both of the locations is/are null!");
        }
    }

    public Cuboid(String cuboidName, String worldName, int priority, double x1, double y1, double z1, double x2, double y2, double z2) {
        if (worldName == null || Bukkit.getServer().getWorld(worldName) == null)
            throw new NullPointerException("One/both of the worlds is/are null!");

        Bukkit.getLogger().info("x1:" + x1 + ",y1:" + y1 + ",z1" + z1 + "  " + "x2:" + x2 + ",y2:" + y2 + ",z2" + z2);

        this.worldName = worldName;
        this.cuboidName = cuboidName;
        this.priority = priority;

        double xPos1 = Math.min(x1, x2);
        double xPos2 = Math.max(x1, x2);
        double yPos1 = Math.min(y1, y2);
        double yPos2 = Math.max(y1, y2);
        double zPos1 = Math.min(z1, z2);
        double zPos2 = Math.max(z1, z2);
        this.minimumPoint = new Vector(xPos1, yPos1, zPos1);
        this.maximumPoint = new Vector(xPos2, yPos2, zPos2);
    }

    public boolean containsLocation(Location location) {
        return location != null && location.getWorld().getName().equals(this.worldName) && location.toVector().isInAABB(this.minimumPoint, this.maximumPoint);
    }

    public boolean containsVector(Vector vector) {
        return vector != null && vector.isInAABB(this.minimumPoint, this.maximumPoint);
    }

    public List<Player> getPlayersIn() {
        List<Player> players = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (containsLocation(player.getLocation()))
                players.add(player);
        }
        return players;
    }

    public List<Block> getBlocks() {
        List<Block> blockList = new ArrayList<>();
        World world = this.getWorld();
        if (world != null) {
            for (int x = this.minimumPoint.getBlockX(); x <= this.maximumPoint.getBlockX(); x++) {
                for (int y = this.minimumPoint.getBlockY(); y <= this.maximumPoint.getBlockY() && y <= world.getMaxHeight(); y++) {
                    for (int z = this.minimumPoint.getBlockZ(); z <= this.maximumPoint.getBlockZ(); z++) {
                        blockList.add(world.getBlockAt(x, y, z));
                    }
                }
            }
        }
        return blockList;
    }

    public Location getLowerLocation() {
        return this.minimumPoint.toLocation(this.getWorld());
    }

    public double getLowerX() {
        return this.minimumPoint.getX();
    }

    public double getLowerY() {
        return this.minimumPoint.getY();
    }

    public double getLowerZ() {
        return this.minimumPoint.getZ();
    }

    public Location getUpperLocation() {
        return this.maximumPoint.toLocation(this.getWorld());
    }

    public double getUpperX() {
        return this.maximumPoint.getX();
    }

    public double getUpperY() {
        return this.maximumPoint.getY();
    }

    public double getUpperZ() {
        return this.maximumPoint.getZ();
    }

    public double getVolume() {
        return (this.getUpperX() - this.getLowerX() + 1) * (this.getUpperY() - this.getLowerY() + 1) * (this.getUpperZ() - this.getLowerZ() + 1);
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    /*public Location getCenter() {
        double x1 = this.getUpperX() + 1;
        double y1 = this.getUpperY() + 1;
        double z1 = this.getUpperZ() + 1;
        return new Location(this.getWorld(), this.getLowerX() + (x1 - this.getLowerX()) / 2.0, this.getLowerY() + (y1 - this.getLowerY()) / 2.0, this.getLowerZ() + (z1 - this.getLowerZ()) / 2.0);
    }*/

    public Location getCenter() {
        int x1 = (int) this.getUpperX() + 1;
        int y1 = (int) this.getUpperY() + 1;
        int z1 = (int) this.getUpperZ() + 1;
        return new Location(this.getWorld(), this.getLowerX() + (x1 - this.getLowerX()) / 2.0, this.getLowerY() + (y1 - this.getLowerY()) / 2.0, this.getLowerZ() + (z1 - this.getLowerZ()) / 2.0);
    }


    public World getWorld() {
        World world = Bukkit.getServer().getWorld(this.worldName);
        if (world == null) throw new NullPointerException("World '" + this.worldName + "' is not loaded.");
        return world;
    }

    public String getName() {
        return this.cuboidName;
    }

    public void setWorld(World world) {
        if (world != null) this.worldName = world.getName();
        else throw new NullPointerException("The world cannot be null.");
    }

    @Override
    public Cuboid clone() {
        return new Cuboid(this);
    }

    @Override
    public ListIterator<Block> iterator() {
        return this.getBlocks().listIterator();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serializedCuboid = new HashMap<>();
        serializedCuboid.put("worldName", this.worldName);
        serializedCuboid.put("cuboidName", this.cuboidName);
        serializedCuboid.put("x1", this.minimumPoint.getX());
        serializedCuboid.put("x2", this.maximumPoint.getX());
        serializedCuboid.put("y1", this.minimumPoint.getY());
        serializedCuboid.put("y2", this.maximumPoint.getY());
        serializedCuboid.put("z1", this.minimumPoint.getZ());
        serializedCuboid.put("z2", this.maximumPoint.getZ());
        return serializedCuboid;
    }

    public static Cuboid deserialize(Map<String, Object> serializedCuboid) {
        try {
            String worldName = (String) serializedCuboid.get("worldName");
            String cuboidName = (String) serializedCuboid.get("cuboidName");
            int id = (Integer) serializedCuboid.get("id");

            double xPos1 = (Double) serializedCuboid.get("x1");
            double xPos2 = (Double) serializedCuboid.get("x2");
            double yPos1 = (Double) serializedCuboid.get("y1");
            double yPos2 = (Double) serializedCuboid.get("y2");
            double zPos1 = (Double) serializedCuboid.get("z1");
            double zPos2 = (Double) serializedCuboid.get("z2");

            return new Cuboid(cuboidName, worldName, 0, xPos1, yPos1, zPos1, xPos2, yPos2, zPos2);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String serializeLoc1() {
        return worldName + "," + minimumPoint.getBlockX() + "," + minimumPoint.getBlockY() + "," + minimumPoint.getBlockZ();
    }

    public String serializeLoc2() {
        return worldName + "," + maximumPoint.getBlockX() + "," + maximumPoint.getBlockY() + "," + maximumPoint.getBlockZ();
    }

    public void shift(CuboidDirection dir, int amount) {
        expand(dir, amount);
        expand(dir.opposite(), -amount);
    }

    public void outset(CuboidDirection dir, int amount) {
        switch (dir) {
            case Horizontal:
                expand(CuboidDirection.North, amount);
                expand(CuboidDirection.South, amount);
                expand(CuboidDirection.East, amount);
                expand(CuboidDirection.West, amount);
                break;
            case Vertical:
                expand(CuboidDirection.Down, amount);
                expand(CuboidDirection.Up, amount);
                break;
            case Both:
                outset(CuboidDirection.Horizontal, amount);
                outset(CuboidDirection.Vertical, amount);
                break;
            default:
                throw new IllegalArgumentException("Invalid direction " + dir);
        }
    }

    public void expand(CuboidDirection dir, int amount) {
        switch (dir) {
            case North:
                this.minimumPoint.setX(this.minimumPoint.getBlockX() - amount);
                break;
            //return new Cuboid(this.id, this.cuboidName, this.worldName, this.priority, this.flags, this.minimumPoint.getBlockX() - amount, this.minimumPoint.getBlockY(), this.minimumPoint.getBlockZ(), this.maximumPoint.getBlockX(), this.maximumPoint.getBlockY(), this.maximumPoint.getBlockZ());
            case South:
                this.maximumPoint.setX(this.maximumPoint.getBlockX() + amount);
                break;
            //return new Cuboid(this.id, this.cuboidName, this.worldName, this.priority, this.flags, this.minimumPoint.getBlockX(), this.minimumPoint.getBlockY(), this.minimumPoint.getBlockZ(), this.maximumPoint.getBlockX() + amount, this.maximumPoint.getBlockY(), this.maximumPoint.getBlockZ());
            case East:
                this.minimumPoint.setZ(this.minimumPoint.getBlockZ() - amount);
                break;
            //return new Cuboid(this.id, this.cuboidName, this.worldName, this.priority, this.flags, this.minimumPoint.getBlockX(), this.minimumPoint.getBlockY(), this.minimumPoint.getBlockZ() - amount, this.maximumPoint.getBlockX(), this.maximumPoint.getBlockY(), this.maximumPoint.getBlockZ());
            case West:
                this.maximumPoint.setZ(this.maximumPoint.getBlockZ() + amount);
                break;
            //return new Cuboid(this.id, this.cuboidName, this.worldName, this.priority, this.flags, this.minimumPoint.getBlockX(), this.minimumPoint.getBlockY(), this.minimumPoint.getBlockZ(), this.maximumPoint.getBlockX(), this.maximumPoint.getBlockY(), this.maximumPoint.getBlockZ() + amount);
            case Down:
                this.minimumPoint.setY(this.minimumPoint.getBlockY() - amount);
                break;
            //return new Cuboid(this.id, this.cuboidName, this.worldName, this.priority, this.flags, this.minimumPoint.getBlockX(), this.minimumPoint.getBlockY() - amount, this.minimumPoint.getBlockZ(), this.maximumPoint.getBlockX(), this.maximumPoint.getBlockY(), this.maximumPoint.getBlockZ());
            case Up:
                this.maximumPoint.setY(this.maximumPoint.getBlockY() + amount);
                break;
            //return new Cuboid(this.id, this.cuboidName, this.worldName, this.priority, this.flags, this.minimumPoint.getBlockX(), this.minimumPoint.getBlockY(), this.minimumPoint.getBlockZ(), this.maximumPoint.getBlockX(), this.maximumPoint.getBlockY() + amount, this.maximumPoint.getBlockZ());
            default:
                throw new IllegalArgumentException("Invalid direction " + dir);
        }
    }

    public enum CuboidDirection {
        North, East, South, West, Up, Down, Horizontal, Vertical, Both, Unknown;

        public CuboidDirection opposite() {
            switch (this) {
                case North:
                    return South;
                case East:
                    return West;
                case South:
                    return North;
                case West:
                    return East;
                case Horizontal:
                    return Vertical;
                case Vertical:
                    return Horizontal;
                case Up:
                    return Down;
                case Down:
                    return Up;
                case Both:
                    return Both;
                default:
                    return Unknown;
            }
        }

    }

}