package pl.subtelny.islands.service;

import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.repository.islander.IslanderRepository;
import pl.subtelny.jobs.JobsProvider;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class IslanderService {

    private final IslanderRepository islanderRepository;

    @Autowired
    public IslanderService(IslanderRepository islanderRepository) {
        this.islanderRepository = islanderRepository;
    }

    public void loadIslander(Player player) {
        JobsProvider.async(() -> loadOrCreateIslanderIfAbsent(player));
    }

    private void loadOrCreateIslanderIfAbsent(Player player) {
        AccountId accountId = getAccountIdFromPlayer(player);
        Optional<Islander> islanderOpt = islanderRepository.findIslander(accountId);

        if (islanderOpt.isEmpty()) {
            createIslander(player);
        }
    }

    private void createIslander(Player player) {
        Islander islander = new Islander(getAccountIdFromPlayer(player));
        islanderRepository.updateIslander(islander);
    }

    public Islander getIslander(Player player) {
        AccountId accountId = getAccountIdFromPlayer(player);
        return islanderRepository
                .getIslanderIfPresent(accountId)
                .orElseThrow(() -> new NoSuchElementException("Not found islander for player " + player.getName()));
    }

    private AccountId getAccountIdFromPlayer(Player player) {
        return AccountId.of(player.getUniqueId());
    }
}
