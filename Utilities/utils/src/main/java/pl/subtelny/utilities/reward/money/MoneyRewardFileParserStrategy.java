package pl.subtelny.utilities.reward.money;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;

import static pl.subtelny.utilities.ConfigUtil.getDouble;

public class MoneyRewardFileParserStrategy extends PathAbstractFileParserStrategy<MoneyReward> {

    private final Economy economy;

    public MoneyRewardFileParserStrategy(File file, Economy economy) {
        super(file);
        this.economy = economy;
    }

    public MoneyRewardFileParserStrategy(YamlConfiguration configuration, File file, Economy economy) {
        super(configuration, file);
        this.economy = economy;
    }

    @Override
    public MoneyReward load(String path) {
        Double money = getDouble(configuration, path + "." + getPath()).orElse(0.0);
        return new MoneyReward(economy, money);
    }

    @Override
    public Saveable set(String path, MoneyReward value) {
        throw new UnsupportedOperationException("Saving Money Reward is not implemented yet");
    }

    @Override
    public String getPath() {
        return "money";
    }
}
