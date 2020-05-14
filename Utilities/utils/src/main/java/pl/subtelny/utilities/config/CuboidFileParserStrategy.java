package pl.subtelny.utilities.config;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.io.File;

public class CuboidFileParserStrategy extends AbstractFileParserStrategy<Cuboid> {

    public CuboidFileParserStrategy(YamlConfiguration configuration, File file) {
        super(configuration, file);
    }

    public CuboidFileParserStrategy(File file) {
        super(file);
    }

    @Override
    public Cuboid load(String path) {
        LocationFileParserStrategy parser = new LocationFileParserStrategy(configuration, file);
        Location loc1 = parser.load(path + ".loc1");
        Location loc2 = parser.load(path + ".loc2");
        return new Cuboid(path, loc1, loc2);
    }

    @Override
    public Saveable set(String path, Cuboid value) {
        LocationFileParserStrategy parser = new LocationFileParserStrategy(configuration, file);
        parser.set(path + ".loc1", value.getLowerLocation());
        parser.set(path + ".loc2", value.getUpperLocation());
        return this;
    }
}
