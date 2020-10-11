package pl.subtelny.gui.session;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.gui.api.crate.inventory.CrateInventory;
import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.gui.api.crate.session.PlayerCrateSession;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.gui.crate.CrateConditionsService;
import pl.subtelny.gui.crate.inventory.CrateInventoryCreator;
import pl.subtelny.gui.crate.model.Crate;
import pl.subtelny.gui.crate.repository.CrateRepository;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.Optional;

@Component
public class PlayerCrateSessionServiceImpl implements PlayerCrateSessionService {

    private final CrateInventoryCreator crateInventoryCreator;

    private final CrateConditionsService conditionsService;

    private final PlayerCrateSessionStorage sessionStorage;

    private final CrateRepository crateRepository;

    @Autowired
    public PlayerCrateSessionServiceImpl(CrateInventoryCreator crateInventoryCreator, CrateConditionsService conditionsService, CrateRepository crateRepository) {
        this.crateInventoryCreator = crateInventoryCreator;
        this.conditionsService = conditionsService;
        this.crateRepository = crateRepository;
        this.sessionStorage = new PlayerCrateSessionStorage();
    }

    @Override
    public Optional<PlayerCrateSession> getSession(Player player) {
        return sessionStorage.getCacheIfPresent(player);
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
    public void openSession(Player player, CrateId crateId) {
        closeInventory(player);
        Crate crate = getCrate(crateId);
        validatePermissionCrate(player, crate);
        CrateInventory inventoryForCrate = crateInventoryCreator.createInv(player, crate);
        PlayerCrateSession session = new PlayerCrateSessionImpl(player, crate, inventoryForCrate, conditionsService);
        sessionStorage.put(player, session);
        session.openCrateInventory();
    }

    private Crate getCrate(CrateId crateId) {
        return crateRepository.findCrate(crateId).orElseThrow(() -> ValidationException.of("crate_repository.findCrate.not_found"));
    }

    private void validatePermissionCrate(Player player, Crate crate) {
        boolean hasPermission = crate.getPermission().map(player::hasPermission).orElse(true);
        Validation.isTrue(hasPermission, "crate.no_permissions");
    }

    @Override
    public void closeAllSessions(Plugin plugin) {
        sessionStorage.getAllCache().entrySet().stream()
                .filter(entry -> entry.getValue().getCrate().getPluginName().equals(plugin.getName()))
                .forEach(entry -> closeInventory(entry.getKey()));
    }

    @Override
    public void closeAllSessions(CrateId crateId) {
        sessionStorage.getAllCache().entrySet().stream()
                .filter(entry -> entry.getValue().getCrate().equals(crateId))
                .forEach(entry -> closeInventory(entry.getKey()));
    }

}
