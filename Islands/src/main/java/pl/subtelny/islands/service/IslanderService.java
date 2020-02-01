package pl.subtelny.islands.service;

import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.repository.islander.IslanderStorage;
import pl.subtelny.jobs.JobsProvider;

import java.util.concurrent.CompletableFuture;

@Component
public class IslanderService {

    private final IslanderStorage islanderStorage;

    @Autowired
    public IslanderService(IslanderStorage islanderStorage) {
        this.islanderStorage = islanderStorage;
    }

    public void createIslanderIfNotExists(Player player) {

    }

}
