package pl.subtelny.islands.island.cqrs.command;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.repository.IslandConfigurationRepository;

@Component
public class IslandConfigurationService {

    private final TransactionProvider transactionProvider;

    private final IslandConfigurationRepository configurationRepository;

    @Autowired
    public IslandConfigurationService(TransactionProvider transactionProvider,
                                      IslandConfigurationRepository configurationRepository) {
        this.transactionProvider = transactionProvider;
        this.configurationRepository = configurationRepository;
    }

    public void updateIslandConfiguration(Island island) {
        transactionProvider.transactionAsync(() -> configurationRepository.saveConfiguration(island));
        ;
    }

}
