package pl.subtelny.utilities.condition.permission;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.file.Saveable;
import java.io.File;

public class PermissionConditionFileParserStrategy extends AbstractFileParserStrategy<PermissionCondition> {

    public PermissionConditionFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
    }

    protected PermissionConditionFileParserStrategy(File file) {
        super(file);
    }

    @Override
    public PermissionCondition load(String path) {
        String permission = configuration.getString(path + ".permission");
        return new PermissionCondition(permission);
    }

    @Override
    public Saveable set(String path, PermissionCondition value) {
        throw new UnsupportedOperationException("Saving PermissionCondition is not supported");
    }

}
