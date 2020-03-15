package pl.subtelny.islands.listeners;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.guard.IslandActionGuard;
import pl.subtelny.islands.guard.IslandActionGuardResult;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.projectiles.ProjectileSource;

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
				IslandActionGuardResult result = islandActionGuard.accessToHit(attacker, livingEntity);
				if (!result.isActionPermited()) {
					e.setIntensity(livingEntity, 0);
				}
			});
		}
	}

}
