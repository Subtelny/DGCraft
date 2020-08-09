package pl.subtelny.core.city.listener;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.city.CityId;
import pl.subtelny.core.city.service.CityPortalTeleporter;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.core.player.CorePlayer;
import pl.subtelny.core.player.CorePlayerService;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class PlayerPortalCityListener implements Listener {

    private static final int PORTAL_TIME_IDLE = 5;

    private final CityPortalTeleporter cityPortalTeleporter;

    private final CoreMessages messages;

    private final CorePlayerService corePlayerService;

    private Cache<Player, Boolean> portalCache = Caffeine.newBuilder()
            .expireAfterWrite(PORTAL_TIME_IDLE, TimeUnit.SECONDS)
            .build();

    @Autowired
    public PlayerPortalCityListener(CityPortalTeleporter cityPortalTeleporter, CoreMessages messages, CorePlayerService corePlayerService) {
        this.cityPortalTeleporter = cityPortalTeleporter;
        this.messages = messages;
        this.corePlayerService = corePlayerService;
    }

    @EventHandler
    public void onPortalEnter(PlayerPortalEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player player = e.getPlayer();
        if (portalCache.getIfPresent(player) != null) {
            messages.sendTo(player, "city.portal.time_idle", PORTAL_TIME_IDLE);
            return;
        }

        Location from = e.getFrom();
        Optional<CityId> cityByPortalLocation = cityPortalTeleporter.findCityByPortalLocation(from);
        cityByPortalLocation.ifPresent(cityId -> {
            e.setCancelled(true);
            portalCache.put(player, true);
            enterPortal(player, cityId);
        });
    }

    private void enterPortal(Player player, CityId cityId) {
        CorePlayer corePlayer = corePlayerService.getCorePlayer(player);
        cityPortalTeleporter.enterPortal(corePlayer, cityId);
    }

}
