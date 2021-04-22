package pl.subtelny.islands.island.cqrs.command;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.database.TransactionProvider;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandConfigurationService;
import pl.subtelny.islands.island.repository.IslandConfigurationRepository;

@Component
public class IslandConfigurationServiceImpl implements IslandConfigurationService {

    private final TransactionProvider transactionProvider;

    private final IslandConfigurationRepository configurationRepository;

    @Autowired
    public IslandConfigurationServiceImpl(TransactionProvider transactionProvider,
                                          IslandConfigurationRepository configurationRepository) {
        this.transactionProvider = transactionProvider;
        this.configurationRepository = configurationRepository;
    }

    @Override
    public void updateIslandConfiguration(Island island) {
        transactionProvider.transactionAsync(() -> configurationRepository.saveConfiguration(island));
    }

}
