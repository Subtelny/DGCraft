package pl.subtelny.islands.islander;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.islander.repository.IslanderRepository;
import pl.subtelny.utilities.job.JobsProvider;
import pl.subtelny.utilities.exception.ValidationException;
import java.util.Optional;

@Component
public class IslanderQueryService extends IslanderService {

    private final IslanderRepository islanderRepository;

    @Autowired
    public IslanderQueryService(IslanderRepository islanderRepository) {
        this.islanderRepository = islanderRepository;
    }

    public Islander getIslander(Player player) {
        IslanderId islanderId = getIslanderId(player);
        return islanderRepository
                .getIslanderIfPresent(islanderId)
                .orElseThrow(() -> new ValidationException("islander.not_found" + player.getName()));
    }
}
