package pl.subtelny.utilities.condition.permission;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.Saveable;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;

public class PermissionConditionFileParserStrategy extends PathAbstractFileParserStrategy<PermissionCondition> {

    public PermissionConditionFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
    }

    protected PermissionConditionFileParserStrategy(File file) {
        super(file);
    }

    @Override
    public String getPath() {
        return "permission";
    }

    @Override
    public PermissionCondition load(String path) {
        String permission = configuration.getString(path + "." + getPath());
        return new PermissionCondition(permission);
    }

    @Override
    public Saveable set(String path, PermissionCondition value) {
        throw new UnsupportedOperationException("Saving PermissionCondition is not supported");
    }

}
