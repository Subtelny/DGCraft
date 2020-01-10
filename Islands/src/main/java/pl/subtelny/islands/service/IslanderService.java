package pl.subtelny.islands.service;

import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.api.Accounts;
import pl.subtelny.core.api.CoreAPI;
import pl.subtelny.core.model.Account;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.repository.storage.IslanderStorage;
import pl.subtelny.islands.repository.synchronizer.IslanderSynchronizer;
import pl.subtelny.jobs.JobsProvider;
import pl.subtelny.validation.ValidationException;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class IslanderService {

    private final IslanderSynchronizer islanderSynchronizer;

    private final IslanderStorage islanderStorage;

    @Autowired
    public IslanderService(IslanderSynchronizer islanderSynchronizer,
                           IslanderStorage islanderStorage) {
        this.islanderSynchronizer = islanderSynchronizer;
        this.islanderStorage = islanderStorage;
    }

    public void loadIslander(Player player) {
        CompletableFuture.runAsync(() -> getIslander(player), JobsProvider.getExecutor());
    }

    public Islander getIslander(Player player) {
        Optional<Islander> islanderOpt = islanderStorage.findIslander(player);
        if (islanderOpt.isPresent()) {
            Islander islander = islanderOpt.get();
            if (!islander.isFullyLoaded()) {
                islanderSynchronizer.synchronizeIslander(islander);
            }
            return islander;
        }
        throw ValidationException.of("Cannot get Islander!");
    }

}
