package pl.subtelny.islands.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.projectiles.ProjectileSource;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.service.IslandActionGuard;
import pl.subtelny.islands.service.IslandService;

@Component
public class PotionSplashEventListener implements Listener {

	private final IslandActionGuard islandActionGuard;

	@Autowired
	public PotionSplashEventListener(IslandActionGuard islandActionGuard) {
		this.islandActionGuard = islandActionGuard;
	}

	@EventHandler
	public void onPotionSplash(PotionSplashEvent e) {
		if (e.isCancelled()) {
			return;
		}
		ProjectileSource shooter = e.getEntity().getShooter();
		if (shooter instanceof Entity) {
			Entity attacker = (Entity) shooter;
			e.getAffectedEntities().forEach(livingEntity -> {
				if (!islandActionGuard.accessToHit(attacker, livingEntity)) {
					e.setIntensity(livingEntity, 0);
				}
			});
		}
	}

}
