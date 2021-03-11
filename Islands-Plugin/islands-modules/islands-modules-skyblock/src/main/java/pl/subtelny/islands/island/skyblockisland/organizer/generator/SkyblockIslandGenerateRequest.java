package pl.subtelny.islands.island.skyblockisland.organizer.generator;

import org.bukkit.Location;

import java.io.File;
import java.util.Objects;

public class SkyblockIslandGenerateRequest {

    private final Location location;

    private final File schematicFile;

    public SkyblockIslandGenerateRequest(Location location, File schematicFile) {
        this.location = location;
        this.schematicFile = schematicFile;
    }

    public Location getLocation() {
        return location;
    }

    public File getSchematicFile() {
        return schematicFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkyblockIslandGenerateRequest that = (SkyblockIslandGenerateRequest) o;
        return Objects.equals(location, that.location) && Objects.equals(schematicFile, that.schematicFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, schematicFile);
    }
}
