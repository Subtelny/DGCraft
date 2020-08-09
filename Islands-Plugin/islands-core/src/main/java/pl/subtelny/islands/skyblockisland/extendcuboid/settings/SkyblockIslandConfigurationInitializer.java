package pl.subtelny.islands.skyblockisland.extendcuboid.settings;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core.api.DependencyActivatorPriority;
import pl.subtelny.core.api.economy.EconomyProvider;
import pl.subtelny.islands.islander.repository.IslanderRepository;
import pl.subtelny.islands.skyblockisland.settings.SkyblockIslandSettings;

@Component
@DependencyActivatorPriority(priority = DependencyActivatorPriority.Priority.LOWEST)
public class SkyblockIslandConfigurationInitializer implements DependencyActivator {

    private final IslanderRepository islanderRepository;

    private final SkyblockIslandSettings skyblockIslandSettings;

    private final EconomyProvider economyProvider;

    @Autowired
    public SkyblockIslandConfigurationInitializer(IslanderRepository islanderRepository, SkyblockIslandSettings skyblockIslandSettings, EconomyProvider economyProvider) {
        this.islanderRepository = islanderRepository;
        this.skyblockIslandSettings = skyblockIslandSettings;
        this.economyProvider = economyProvider;
    }

    @Override
    public void activate(Plugin plugin) {
        Economy economy = economyProvider.getEconomy();
        skyblockIslandSettings.initConfig(plugin, economy, islanderRepository);
    }

}
