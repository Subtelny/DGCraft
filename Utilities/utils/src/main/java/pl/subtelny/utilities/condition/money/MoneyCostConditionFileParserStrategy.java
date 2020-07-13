package pl.subtelny.utilities.condition.money;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.Saveable;

import java.io.File;

public class MoneyCostConditionFileParserStrategy extends AbstractFileParserStrategy<MoneyCostCondition> {

    private final Economy economy;

    public MoneyCostConditionFileParserStrategy(YamlConfiguration configuration, File file, Economy economy) {
        super(configuration, file);
        this.economy = economy;
    }

    protected MoneyCostConditionFileParserStrategy(File file, Economy economy) {
        super(file);
        this.economy = economy;
    }

    @Override
    public MoneyCostCondition load(String path) {
        MoneyCondition moneyCondition = new MoneyConditionFileParserStrategy(configuration, file, economy).load(path);
        return new MoneyCostCondition(moneyCondition);
    }

    @Override
    public Saveable set(String path, MoneyCostCondition value) {
        throw new UnsupportedOperationException("Saving MoneyCostCondition is not implemented yet");
    }

}
