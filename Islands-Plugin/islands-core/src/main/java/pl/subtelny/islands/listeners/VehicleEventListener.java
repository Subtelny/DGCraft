package pl.subtelny.islands.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.guard.IslandActionGuard;
import pl.subtelny.islands.guard.IslandActionGuardResult;

@Component
public class VehicleEventListener implements Listener {

    private final IslandActionGuard islandActionGuard;

    @Autowired
    public VehicleEventListener(IslandActionGuard islandActionGuard) {
        this.islandActionGuard = islandActionGuard;
    }

    @EventHandler(ignoreCancelled = true)
    public void onVehicleEnter(VehicleEnterEvent e) {
        Entity entered = e.getEntered();
        Vehicle vehicle = e.getVehicle();

        IslandActionGuardResult result = islandActionGuard.accessToMount(entered, vehicle);
        if (result.isActionProhibited()) {
            e.setCancelled(true);
        }
    }

}
