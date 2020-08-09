package pl.subtelny.gui.session;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.gui.api.crate.inventory.CrateInventory;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.api.crate.session.PlayerCrateSession;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.gui.crate.CrateConditionsService;
import pl.subtelny.gui.crate.CrateService;

import java.util.Optional;

@Component
public class PlayerCrateSessionServiceImpl implements PlayerCrateSessionService {

    private final CrateService crateService;

    private final CrateConditionsService conditionsService;

    private final PlayerCrateSessionStorage sessionStorage;

    @Autowired
    public PlayerCrateSessionServiceImpl(CrateService crateService, CrateConditionsService conditionsService) {
        this.crateService = crateService;
        this.conditionsService = conditionsService;
        this.sessionStorage = new PlayerCrateSessionStorage();
    }

    @Override
    public Optional<PlayerCrateSession> getSession(Player player) {
        return Optional.ofNullable(sessionStorage.getCacheIfPresent(player));
    }

    @Override
    public boolean hasSession(Player player) {
        return getSession(player).isPresent();
    }

    @Override
    public void closeSession(Player player) {
        Optional<PlayerCrateSession> sessionOpt = getSession(player);
        sessionOpt.ifPresent(session -> sessionStorage.invalidate(player));
    }

    @Override
    public void closeInventory(Player player) {
        Optional<PlayerCrateSession> sessionOpt = getSession(player);
        sessionOpt.ifPresent(session -> {
            sessionStorage.invalidate(player);
            session.closeCrateInventory();
        });
    }

    @Override
    public void openSession(Player player, Crate crate) {
        closeInventory(player);
        CrateInventory inventoryForCrate = crateService.getInventoryForCrate(player, crate);
        PlayerCrateSession session = new PlayerCrateSessionImpl(player, crate, inventoryForCrate, conditionsService);
        sessionStorage.put(player, session);
        session.openCrateInventory();
    }

    @Override
    public void closeAllSessions(Plugin plugin) {
        sessionStorage.getAllCache().entrySet().stream()
                .filter(entry -> entry.getValue().getCrate().getId().getPluginName().equals(plugin.getName()))
                .forEach(entry -> closeInventory(entry.getKey()));
    }

}
