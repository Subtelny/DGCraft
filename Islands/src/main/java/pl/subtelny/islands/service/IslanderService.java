package pl.subtelny.islands.service;

import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.repository.islander.IslanderRepository;
import pl.subtelny.jobs.JobsProvider;

import java.util.Optional;

@Component
public class IslanderService {

    private final IslanderRepository islanderRepository;

    @Autowired
    public IslanderService(IslanderRepository islanderRepository) {
        this.islanderRepository = islanderRepository;
    }

    public void createIslanderIfNotExists(Player player) {
        JobsProvider.async(() -> {
            AccountId accountId = AccountId.of(player.getUniqueId());
            Optional<Islander> islander = islanderRepository.findIslander(accountId);
            if (islander.isEmpty()) {

            }
        });
    }

}
