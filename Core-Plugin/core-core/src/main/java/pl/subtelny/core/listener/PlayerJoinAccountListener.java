package pl.subtelny.core.listener;

import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.Core;
import pl.subtelny.core.api.account.Accounts;
import pl.subtelny.core.configuration.Messages;
import pl.subtelny.jobs.JobsProvider;
import pl.subtelny.utilities.MessageUtil;
import pl.subtelny.utilities.PlayerUtil;
import pl.subtelny.utilities.location.LocationUtil;
import pl.subtelny.utilities.log.Log;

import javax.annotation.Nullable;
import java.util.Set;

@Component
public class PlayerJoinAccountListener implements Listener {

    private static final String PLAYER_DATA_IS_LOADING = "player_data_is_loading";

    private static final String ACCOUNT_DATA_LOADED_EXCEPTIONALLY = "account_data_loaded_exceptionally";

    private final Set<Player> markerPlayers = Sets.newConcurrentHashSet();

    private final Messages messages;

    private final Accounts accounts;

    @Autowired
    public PlayerJoinAccountListener(Messages messages, Accounts accounts) {
        this.messages = messages;
        this.accounts = accounts;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        markPlayer(player);
        accounts.findAccountAsync(player)
                .whenComplete((account, throwable) -> unmarkPlayer(player))
                .handle((aVoid, throwable) -> {
                    dataLoadedExceptionally(player, throwable);
                    return throwable;
                });
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Location to = e.getTo();
        if (to == null || LocationUtil.isSameLocationPrecisionToBlock(e.getFrom(), to)) {
            return;
        }
        Player player = e.getPlayer();
        if (isPlayerMarked(player)) {
            e.setCancelled(true);
            MessageUtil.message(player, messages.get(PLAYER_DATA_IS_LOADING));
        }
    }

    private void dataLoadedExceptionally(@Nullable Player player, Throwable throwable) {
        if (player != null) {
            Log.warning(String.format("Player %s kicked of data load failed reason: %s ", player.getName(), throwable.getMessage()));
            JobsProvider.runSync(Core.plugin, () -> {
                String message = messages.get(ACCOUNT_DATA_LOADED_EXCEPTIONALLY);
                PlayerUtil.kick(player, message);
            });
        }
    }

    private void markPlayer(Player player) {
        markerPlayers.add(player);
    }

    private void unmarkPlayer(Player player) {
        markerPlayers.remove(player);
    }

    private boolean isPlayerMarked(Player player) {
        return markerPlayers.contains(player);
    }

}
