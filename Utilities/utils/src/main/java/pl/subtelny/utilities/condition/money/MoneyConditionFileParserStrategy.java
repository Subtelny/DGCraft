package pl.subtelny.utilities.condition.money;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;

public class MoneyConditionFileParserStrategy extends PathAbstractFileParserStrategy<MoneyCondition> {

    private final Economy economy;

    public MoneyConditionFileParserStrategy(YamlConfiguration configuration, File file, Economy economy) {
        super(configuration, file);
        this.economy = economy;
    }

    public MoneyConditionFileParserStrategy(File file, Economy economy) {
        super(file);
        this.economy = economy;
    }

    @Override
    public MoneyCondition load(String path) {
        double money = configuration.getDouble(path + "." + getPath());
        return new MoneyCondition(economy, money);
    }

    @Override
    public Saveable set(String path, MoneyCondition value) {
        throw new UnsupportedOperationException("Saving MoneyCondition is not implemented yet");
    }

    @Override
    public String getPath() {
        return "money";
    }
}
