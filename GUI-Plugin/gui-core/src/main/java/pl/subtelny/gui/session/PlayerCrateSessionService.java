package pl.subtelny.gui.session;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.gui.api.crate.inventory.CrateInventory;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.crate.CrateService;
import pl.subtelny.gui.messages.CrateMessages;

import java.util.Optional;

@Component
public class PlayerCrateSessionService {

    private final CrateService crateService;

    private final CrateSessionStorage sessionStorage;

    private final CrateMessages messages;

    @Autowired
    public PlayerCrateSessionService(CrateService crateService, CrateMessages messages) {
        this.crateService = crateService;
        this.messages = messages;
        this.sessionStorage = new CrateSessionStorage();
    }

    public Optional<PlayerCrateSession> getSession(Player player) {
        return Optional.ofNullable(sessionStorage.getCacheIfPresent(player));
    }

    public boolean hasSession(Player player) {
        return getSession(player).isPresent();
    }

    public void closeSession(Player player) {
        Optional<PlayerCrateSession> sessionOpt = getSession(player);
        sessionOpt.ifPresent(session -> sessionStorage.invalidate(player));
    }

    public void closeInventory(Player player) {
        Optional<PlayerCrateSession> sessionOpt = getSession(player);
        sessionOpt.ifPresent(session -> {
            sessionStorage.invalidate(player);
            session.closeCrateInventory();
        });
    }

    public void openSession(Player player, Crate crate) {
        closeInventory(player);
        CrateInventory inventoryForCrate = crateService.getInventoryForCrate(player, crate);
        PlayerCrateSession session = new PlayerCrateSession(player, crate, inventoryForCrate, crateService, messages);
        sessionStorage.put(player, session);
        session.openCrateInventory();
    }

    public void closeAllSessions(Plugin plugin) {
        sessionStorage.getAllCache().entrySet().stream()
                .filter(entry -> entry.getValue().getCrate().getId().getPluginName().equals(plugin.getName()))
                .forEach(entry -> closeInventory(entry.getKey()));
    }

}
