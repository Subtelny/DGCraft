package pl.subtelny.core.player;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.bukkit.entity.Player;
import pl.subtelny.repository.Storage;

public class CorePlayerStorage extends Storage<Player, CorePlayer> {

    public CorePlayerStorage() {
        super(Caffeine.newBuilder().build());
    }
}
