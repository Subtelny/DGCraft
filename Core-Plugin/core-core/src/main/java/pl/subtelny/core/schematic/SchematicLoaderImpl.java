package pl.subtelny.core.schematic;

import org.bukkit.Location;
import org.primesoft.asyncworldedit.api.blockPlacer.IBlockPlacerListener;
import org.primesoft.asyncworldedit.api.blockPlacer.entries.IJobEntry;
import org.primesoft.asyncworldedit.api.blockPlacer.entries.JobStatus;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.schematic.SchematicLoader;
import pl.subtelny.core.worldedit.WorldEditHelper;
import pl.subtelny.utilities.Callback;

import java.io.File;
import java.io.IOException;

@Component
public class SchematicLoaderImpl implements SchematicLoader {

    @Override
    public void pasteSchematic(Location location, File schematic, Callback<Boolean> callback) {
        try {
            WorldEditHelper.pasteSchematic(location, schematic, new IBlockPlacerListener() {
                @Override
                public void jobAdded(IJobEntry iJobEntry) {

                }

                @Override
                public void jobRemoved(IJobEntry iJobEntry) {
                    if (iJobEntry.getStatus() == JobStatus.Done) {
                        callback.done(true);
                    } else {
                        callback.done(false);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
