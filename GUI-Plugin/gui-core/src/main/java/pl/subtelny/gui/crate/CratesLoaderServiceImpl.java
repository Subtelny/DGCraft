package pl.subtelny.gui.crate;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.gui.api.crate.CratesLoaderService;
import pl.subtelny.gui.api.crate.CrateLoadRequest;
import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.crate.repository.CrateRepository;
import pl.subtelny.utilities.Validation;

@Component
public class CratesLoaderServiceImpl implements CratesLoaderService {

    private final CratesFileLoader cratesFileLoader;

    private final CrateRepository crateRepository;

    private final PlayerCrateSessionService playerCrateSessionService;

    @Autowired
    public CratesLoaderServiceImpl(CratesFileLoader cratesFileLoader, CrateRepository crateRepository, PlayerCrateSessionService playerCrateSessionService) {
        this.cratesFileLoader = cratesFileLoader;
        this.crateRepository = crateRepository;
        this.playerCrateSessionService = playerCrateSessionService;
    }

    @Override
    public void unloadAllCrates(Plugin plugin) {
        playerCrateSessionService.closeAllSessions(plugin);
        crateRepository.removeAll(plugin);
    }

    @Override
    public void unloadCrate(CrateId crateId) {
        playerCrateSessionService.closeAllSessions(crateId);
        crateRepository.removeCrate(crateId);
    }

    @Override
    public void loadCrate(CrateLoadRequest request) {
        Crate crate = cratesFileLoader.loadCrate(request);
        validateAlreadyLoaded(crate.getId());
        crateRepository.addCrate(crate);
    }

    private void validateAlreadyLoaded(CrateId crateId) {
        Validation.isTrue(crateRepository.findCrate(crateId).isEmpty(), "Crate %s already loaded", crateId);
    }

}
