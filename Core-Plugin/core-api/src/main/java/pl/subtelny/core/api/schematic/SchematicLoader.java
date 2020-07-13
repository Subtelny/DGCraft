package pl.subtelny.core.api.schematic;

import org.bukkit.Location;
import pl.subtelny.utilities.Callback;

import java.io.File;

public interface SchematicLoader {

    void pasteSchematic(Location location, File schematic, Callback<Boolean> callback);

}
