package pl.subtelny.islands.skyblockisland.condition;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.islands.islander.repository.IslanderRepository;
import pl.subtelny.utilities.file.AbstractFileParserStrategy;
import pl.subtelny.utilities.Saveable;

import java.io.File;

public class SkyblockIslandPointsConditionFileParserStrategy extends AbstractFileParserStrategy<SkyblockIslandPointsCondition> {

    private final IslanderRepository islanderRepository;

    public SkyblockIslandPointsConditionFileParserStrategy(YamlConfiguration configuration, File file, IslanderRepository islanderRepository) {
        super(configuration, file);
        this.islanderRepository = islanderRepository;
    }

    protected SkyblockIslandPointsConditionFileParserStrategy(File file, IslanderRepository islanderRepository) {
        super(file);
        this.islanderRepository = islanderRepository;
    }

    @Override
    public SkyblockIslandPointsCondition load(String path) {
        int islandPoints = configuration.getInt(path + ".island_points");
        return new SkyblockIslandPointsCondition(islanderRepository, islandPoints);
    }

    @Override
    public Saveable set(String path, SkyblockIslandPointsCondition value) {
        throw new UnsupportedOperationException("Saving PermissionCondition is not supported");
    }

}
