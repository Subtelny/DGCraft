package pl.subtelny.core.listener;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.CityType;
import pl.subtelny.core.city.service.CityPortalTeleporter;
import pl.subtelny.core.city.service.CityService;
import pl.subtelny.core.configuration.Messages;
import pl.subtelny.utilities.MessageUtil;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class PlayerPortalCityListener implements Listener {

    private static final int PORTAL_TIME_IDLE = 5;

    private final CityPortalTeleporter cityPortalTeleporter;

    private final Messages messages;

    private Cache<Player, Boolean> portalCache = Caffeine.newBuilder()
            .expireAfterWrite(PORTAL_TIME_IDLE, TimeUnit.SECONDS)
            .build();

    @Autowired
    public PlayerPortalCityListener(CityPortalTeleporter cityPortalTeleporter, Messages messages) {
        this.cityPortalTeleporter = cityPortalTeleporter;
        this.messages = messages;
    }

    @EventHandler
    public void onPortalEnter(PlayerPortalEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player player = e.getPlayer();
        if (portalCache.getIfPresent(player) != null) {
            MessageUtil.message(player, String.format(messages.get("wait_time_idle_portal"), PORTAL_TIME_IDLE));
            return;
        }

        Location from = e.getFrom();
        Optional<CityType> cityByPortalLocation = cityPortalTeleporter.findCityByPortalLocation(from);
        if (cityByPortalLocation.isPresent()) {
            e.setCancelled(true);
            portalCache.put(player, true);
            cityPortalTeleporter.enterPortal(player, cityByPortalLocation.get());
        }
    }

}
