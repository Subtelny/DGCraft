package pl.subtelny.utilities.reward.money;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.subtelny.utilities.ConfigUtil;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.Saveable;
import pl.subtelny.utilities.item.ItemStackFileParserStrategy;
import pl.subtelny.utilities.reward.itemstack.ItemStackReward;

import java.io.File;
import java.util.Optional;

import static pl.subtelny.utilities.ConfigUtil.getDouble;
import static pl.subtelny.utilities.ConfigUtil.getInt;

public class MoneyRewardFileParserStrategy extends AbstractFileParserStrategy<MoneyReward> {

    private final Economy economy;

    public MoneyRewardFileParserStrategy(YamlConfiguration configuration, File file, Economy economy) {
        super(configuration, file);
        this.economy = economy;
    }

    @Override
    public MoneyReward load(String path) {
        Double money = getDouble(configuration, path + ".money").orElse(0.0);
        return new MoneyReward(economy, money);
    }

    @Override
    public Saveable set(String path, MoneyReward value) {
        throw new UnsupportedOperationException("Saving Money Reward is not implemented yet");
    }
}
