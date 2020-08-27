package pl.subtelny.core.dbupgrade;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core.api.DependencyActivatorPriority;
import pl.subtelny.core.api.upgrade.DatabaseUpgrade;

import java.util.Comparator;
import java.util.List;

@Component
@DependencyActivatorPriority(priority = DependencyActivatorPriority.Priority.LOWEST)
public class DatabaseUpgradeActivator implements DependencyActivator {

    private final List<DatabaseUpgrade> upgrades;

    @Autowired
    public DatabaseUpgradeActivator(List<DatabaseUpgrade> upgrades) {
        this.upgrades = upgrades;
    }

    @Override
    public void activate(Plugin plugin) {
        upgrades.stream()
                .sorted(Comparator.comparingInt(DatabaseUpgrade::order))
                .forEach(DatabaseUpgrade::execute);
    }
}
