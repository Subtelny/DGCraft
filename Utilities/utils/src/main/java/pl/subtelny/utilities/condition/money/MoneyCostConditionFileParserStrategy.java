package pl.subtelny.utilities.condition.money;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;

public class MoneyCostConditionFileParserStrategy extends PathAbstractFileParserStrategy<MoneyCostCondition> {

    private final MoneyConditionFileParserStrategy parserStrategy;

    public MoneyCostConditionFileParserStrategy(YamlConfiguration configuration, File file, Economy economy) {
        super(configuration, file);
        this.parserStrategy = new MoneyConditionFileParserStrategy(configuration, file, economy);
    }

    public MoneyCostConditionFileParserStrategy(File file, Economy economy) {
        super(file);
        this.parserStrategy = new MoneyConditionFileParserStrategy(configuration, file, economy);
    }

    @Override
    public MoneyCostCondition load(String path) {
        MoneyCondition moneyCondition = parserStrategy.load(path);
        return new MoneyCostCondition(moneyCondition);
    }

    @Override
    public Saveable set(String path, MoneyCostCondition value) {
        throw new UnsupportedOperationException("Saving MoneyCostCondition is not implemented yet");
    }

    @Override
    public String getPath() {
        return parserStrategy.getPath();
    }
}
