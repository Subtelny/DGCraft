package pl.subtelny.utilities.condition.money;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.Saveable;

import java.io.File;

public class MoneyConditionFileParserStrategy extends AbstractFileParserStrategy<MoneyCondition> {

    private final Economy economy;

    public MoneyConditionFileParserStrategy(YamlConfiguration configuration, File file, Economy economy) {
        super(configuration, file);
        this.economy = economy;
    }

    protected MoneyConditionFileParserStrategy(File file, Economy economy) {
        super(file);
        this.economy = economy;
    }

    @Override
    public MoneyCondition load(String path) {
        double money = configuration.getDouble(path + ".money");
        return new MoneyCondition(economy, money);
    }

    @Override
    public Saveable set(String path, MoneyCondition value) {
        throw new UnsupportedOperationException("Saving MoneyCondition is not implemented yet");
    }

}
