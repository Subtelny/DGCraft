package pl.subtelny.core.cuboidselector;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class CuboidSelectService {

    private Map<Player, CuboidSelectSession> cuboidSelects = new HashMap<>();

    public void createSession(Player player, Consumer<Cuboid> consumer) {
        Validate.notNull(player, "Player cannot be null to create cuboid select session");
        if (cuboidSelects.containsKey(player)) {
            throw ValidationException.of("Cuboid select session is already created for player " + player.getName());
        }
        cuboidSelects.put(player, new CuboidSelectSession(cuboid -> {
            cuboidSelects.remove(player);
            consumer.accept(cuboid);
        }));
    }

    public CuboidSelectSession getCuboidSelect(Player player) {
        return cuboidSelects.get(player);
    }

}
