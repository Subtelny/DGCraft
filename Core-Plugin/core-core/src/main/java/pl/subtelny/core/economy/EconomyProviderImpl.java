package pl.subtelny.core.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core.api.DependencyActivatorPriority;
import pl.subtelny.core.api.economy.EconomyProvider;
import pl.subtelny.utilities.exception.ValidationException;

import static org.bukkit.Bukkit.getServer;

@Component
@DependencyActivatorPriority(priority = DependencyActivatorPriority.Priority.LOWEST)
public class EconomyProviderImpl implements EconomyProvider, DependencyActivator {

    private Economy economy;

    public EconomyProviderImpl() { }

    @Override
    public void activate(Plugin plugin) {
        this.economy = setupEconomy();
    }

    private Economy setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            throw ValidationException.of("Could not found Vault plugin!");
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            throw ValidationException.of("Could not found any provider for economy");
        }
        return rsp.getProvider();
    }

    @Override
    public Economy getEconomy() {
        return economy;
    }
}
