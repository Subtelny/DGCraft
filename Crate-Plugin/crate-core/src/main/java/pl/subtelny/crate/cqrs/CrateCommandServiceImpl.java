package pl.subtelny.crate.cqrs;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.command.CrateCommandService;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.inventory.CrateInventory;
import pl.subtelny.crate.repository.CrateRepository;

import java.util.Collection;

@Component
public class CrateCommandServiceImpl implements CrateCommandService {

    private final CrateRepository crateRepository;

    @Autowired
    public CrateCommandServiceImpl(CrateRepository crateRepository) {
        this.crateRepository = crateRepository;
    }

    @Override
    public void closeAll() {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getOpenInventory().getTopInventory() instanceof CrateInventory)
                .forEach(player -> player.closeInventory(InventoryCloseEvent.Reason.CANT_USE));
    }

    @Override
    public void closeAll(Plugin plugin) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getOpenInventory().getTopInventory() instanceof CrateInventory)
                .filter(player -> ((CrateInventory) player.getOpenInventory().getTopInventory()).getCrateId().getPluginName().equals(player.getName()))
                .forEach(player -> player.closeInventory(InventoryCloseEvent.Reason.CANT_USE));
    }

    @Override
    public void closeAll(Collection<CrateId> crateIds) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getOpenInventory().getTopInventory() instanceof CrateInventory)
                .filter(player -> crateIds.contains(((CrateInventory) player.getOpenInventory().getTopInventory()).getCrateId()))
                .forEach(player -> player.closeInventory(InventoryCloseEvent.Reason.CANT_USE));
    }

    @Override
    public void closeAll(CrateId crateId) {
        crateRepository.findGlobalCrate(crateId)
                .ifPresentOrElse(Crate::closeAllSessions,
                        () -> closeAllSessions(crateId));
    }

    @Override
    public void register(CratePrototype cratePrototype) {
        crateRepository.addCratePrototype(cratePrototype.getCrateId(), cratePrototype);
    }

    @Override
    public void unregister(CrateId crateId) {
        closeAll(crateId);
        crateRepository.removeCratePrototype(crateId);
    }

    @Override
    public void unregisterAll(Collection<CrateId> crateIds) {
        closeAll(crateIds);
        crateIds.forEach(crateRepository::removeCratePrototype);
    }

    @Override
    public void unregisterAll(Plugin plugin) {
        closeAll(plugin);
        crateRepository.removeCrates(plugin);
    }

    private void closeAllSessions(CrateId crateId) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getOpenInventory().getTopInventory() instanceof CrateInventory)
                .filter(player -> ((CrateInventory) player.getOpenInventory().getTopInventory()).getCrateId().equals(crateId))
                .findAny()
                .ifPresent(player -> player.closeInventory(InventoryCloseEvent.Reason.CANT_USE));
    }

}
