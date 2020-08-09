package pl.subtelny.islands.islander;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.islander.repository.IslanderRepository;
import pl.subtelny.jobs.JobsProvider;
import pl.subtelny.utilities.exception.ValidationException;
import java.util.Optional;

@Component
public class IslanderService {

    private final IslanderRepository islanderRepository;

    @Autowired
    public IslanderService(IslanderRepository islanderRepository) {
        this.islanderRepository = islanderRepository;
    }

    public void loadIslander(Player player) {
        JobsProvider.runAsync(() -> loadOrCreateIslanderIfAbsent(player));
    }

    private void loadOrCreateIslanderIfAbsent(Player player) {
        IslanderId islanderId = getIslanderId(player);
        Optional<Islander> islanderOpt = islanderRepository.findIslander(islanderId);

        if (islanderOpt.isEmpty()) {
            createIslander(player);
        }
    }

    private void createIslander(Player player) {
        Islander islander = new Islander(getIslanderId(player));
        islanderRepository.updateIslander(islander);
    }

    public Islander getIslander(Player player) {
        IslanderId islanderId = getIslanderId(player);
        return islanderRepository
                .getIslanderIfPresent(islanderId)
                .orElseThrow(() -> new ValidationException("islander.not_found" + player.getName()));
    }

    private IslanderId getIslanderId(Player player) {
        return IslanderId.of(player.getUniqueId());
    }
}
