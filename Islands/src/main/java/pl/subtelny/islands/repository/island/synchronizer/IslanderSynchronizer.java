package pl.subtelny.islands.repository.island.synchronizer;

import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.island.storage.SkyblockIslandStorage;
import pl.subtelny.jobs.JobsProvider;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class IslanderSynchronizer extends Synchronizer<Islander> {

	private final SkyblockIslandStorage skyblockIslandStorage;

	@Autowired
	public IslanderSynchronizer(SkyblockIslandStorage skyblockIslandStorage) {
		this.skyblockIslandStorage = skyblockIslandStorage;
	}

	public void forceSynchronizeIslander(Islander islander) {
		synchronizeIslander(islander, true);
	}

	public void synchronizeIslander(Islander islander) {
		synchronizeIslander(islander, false);
	}

	private void synchronizeIslander(Islander islander, boolean force) {
		if (preventLoad(islander, force)) {
			return;
		}
		lock(islander);
		try {
			if (!preventLoad(islander, force)) {
				CompletableFuture<Void> future = CompletableFuture.runAsync(() ->
						synchronizeIslanderIsland(islander), JobsProvider.getExecutor());
				future.get();
				islander.setFullyLoaded(true);
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			unlock(islander);
		}
	}

	private boolean preventLoad(Islander islander, boolean force) {
		if (force) {
			return false;
		}
		return islander.isFullyLoaded();
	}

	private void synchronizeIslanderIsland(Islander islander) {
		Optional<SkyblockIsland> islandOpt = skyblockIslandStorage.findSkyblockIslandByIslander(islander);
		Optional<SkyblockIsland> currentIslandOpt = islander.getIsland();

		if (islandOpt.isPresent()) {
			SkyblockIsland island = islandOpt.get();
			if (currentIslandOpt.isEmpty()) {
				if (island.isInIsland(islander)) {
					islander.setIsland(island);
				} else {
					island.addMember(islander);
				}
			} else {
				SkyblockIsland currentIsland = currentIslandOpt.get();
				if (currentIsland.isInIsland(islander)) {
					currentIsland.removeMember(islander);
				} else {
					islander.setIsland(null);
					island.addMember(islander);
				}
			}
		} else {
			if (currentIslandOpt.isPresent()) {
				SkyblockIsland currentIsland = currentIslandOpt.get();
				if (currentIsland.isInIsland(islander)) {
					currentIsland.removeMember(islander);
				} else {
					islander.setIsland(null);
				}
			}
		}

	}

}
