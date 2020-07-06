package pl.subtelny.core.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.economy.EconomyProvider;
import pl.subtelny.utilities.exception.ValidationException;

import static org.bukkit.Bukkit.getServer;

@Component
public class EconomyProviderImpl implements EconomyProvider {

    private final Economy economy;

    public EconomyProviderImpl() {
        this.economy = setupEconomy();
    }

    @Override
    public Economy getEconomy() {
        return economy;
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
}
