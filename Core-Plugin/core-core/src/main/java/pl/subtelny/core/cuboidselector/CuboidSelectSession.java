package pl.subtelny.core.cuboidselector;

import org.bukkit.Location;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.function.Consumer;

public class CuboidSelectSession {

    private final Consumer<Cuboid> consumer;

    private Location positionOne;

    private Location positionTwo;

    public CuboidSelectSession(Consumer<Cuboid> consumer) {
        this.consumer = consumer;
    }

    public void setPositionOne(Location positionOne) {
        this.positionOne = positionOne;
    }

    public void setPositionTwo(Location positionTwo) {
        this.positionTwo = positionTwo;
    }

    public boolean isReady() {
        return positionOne != null && positionTwo != null;
    }

    public void createCuboid() {
        if (!isReady()) {
            throw ValidationException.of("Not all locations are set to create cuboid");
        }
        Cuboid cuboid = new Cuboid(this.getClass().getName(), positionOne, positionTwo);
        consumer.accept(cuboid);
    }
}
