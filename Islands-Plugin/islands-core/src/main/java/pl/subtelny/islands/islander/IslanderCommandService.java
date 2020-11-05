package pl.subtelny.islands.islander;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.repository.IslanderRepository;
import pl.subtelny.utilities.job.JobsProvider;

import java.util.Optional;

@Component
public class IslanderCommandService extends IslanderService {

    private final IslanderRepository islanderRepository;

    @Autowired
    public IslanderCommandService(IslanderRepository islanderRepository) {
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
        IslanderId islanderId = getIslanderId(player);
        islanderRepository.createIslander(islanderId);
    }

}
