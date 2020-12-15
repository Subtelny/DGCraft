package pl.subtelny.crate.cqrs;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.inventory.CrateInventory;
import pl.subtelny.crate.repository.CrateRepository;

@Component
public class CrateCommandService {

    private final CrateRepository crateRepository;

    @Autowired
    public CrateCommandService(CrateRepository crateRepository) {
        this.crateRepository = crateRepository;
    }

    public void closeAll() {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getOpenInventory().getTopInventory() instanceof CrateInventory)
                .forEach(player -> player.closeInventory(InventoryCloseEvent.Reason.CANT_USE));
    }

    public void closeAll(Plugin plugin) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getOpenInventory().getTopInventory() instanceof CrateInventory)
                .filter(player -> ((CrateInventory) player.getOpenInventory().getTopInventory()).getCrateId().getPluginName().equals(player.getName()))
                .forEach(player -> player.closeInventory(InventoryCloseEvent.Reason.CANT_USE));
    }

    public void closeAll(CrateId crateId) {
        crateRepository.findGlobalCrate(crateId)
                .ifPresentOrElse(Crate::closeAllSessions,
                        () -> closeAllSessions(crateId));
    }

    private void closeAllSessions(CrateId crateId) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getOpenInventory().getTopInventory() instanceof CrateInventory)
                .filter(player -> ((CrateInventory) player.getOpenInventory().getTopInventory()).getCrateId().equals(crateId))
                .findAny()
                .ifPresent(player -> player.closeInventory(InventoryCloseEvent.Reason.CANT_USE));
    }


}
