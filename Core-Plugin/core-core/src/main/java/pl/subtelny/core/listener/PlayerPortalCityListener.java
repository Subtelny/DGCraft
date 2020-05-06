package pl.subtelny.core.listener;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.city.CityPortal;
import pl.subtelny.core.city.CityService;
import pl.subtelny.core.configuration.Messages;
import pl.subtelny.utilities.MessageUtil;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class PlayerPortalCityListener implements Listener {

    private static final int PORTAL_TIME_IDLE = 5;

    private static final String WAIT_TIME_IDLE_PORTAL = "wait_time_idle_portal";

    private final CityService cityService;

    private final Messages messages;

    private Cache<Player, Boolean> portalCache = Caffeine.newBuilder()
            .expireAfterWrite(PORTAL_TIME_IDLE, TimeUnit.SECONDS)
            .build();

    @Autowired
    public PlayerPortalCityListener(CityService cityService, Messages messages) {
        this.cityService = cityService;
        this.messages = messages;
    }

    @EventHandler
    public void onPortalEnter(PlayerPortalEvent e) {
        if (e.isCancelled()) {
            return;
        }

        Player player = e.getPlayer();
        if (portalCache.getIfPresent(player) != null) {
            MessageUtil.message(player, String.format(messages.get(WAIT_TIME_IDLE_PORTAL), PORTAL_TIME_IDLE));
            return;
        }
        Optional<CityPortal> cityOpt = cityService.findPortalAtLocation(e.getFrom());
        if (cityOpt.isPresent()) {
            e.setCancelled(true);
            cityService.enterCityPortal(player, cityOpt.get());
        }
    }

}
