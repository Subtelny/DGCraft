package pl.subtelny.gui.listeners;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.api.crate.repository.CrateRepository;
import pl.subtelny.gui.events.CrateInventoryChangeEvent;
import pl.subtelny.gui.session.PlayerCrateSessionService;

import java.util.Optional;

@Component
public class InventoryChangeListener implements Listener {

    private final PlayerCrateSessionService sessionService;

    private final CrateRepository crateRepository;

    @Autowired
    public InventoryChangeListener(PlayerCrateSessionService sessionService, CrateRepository crateRepository) {
        this.sessionService = sessionService;
        this.crateRepository = crateRepository;
    }

    @EventHandler()
    public void onInventory(CrateInventoryChangeEvent e) {
        HumanEntity entity = e.getPlayer();
        if (!(entity instanceof Player)) {
            return;
        }
        Optional<Crate> crateOpt = crateRepository.findCrate(e.getCrateId());
        crateOpt.ifPresent(crate -> sessionService.openSession((Player) entity, crate));
    }
}
