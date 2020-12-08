package pl.subtelny.islands.island.skyblockisland.creator;

import pl.subtelny.islands.islander.model.Islander;

import java.io.File;
import java.util.Objects;

public class SkyblockIslandCreateRequest {

    private final Islander owner;

    private final File schematicLevel;

    private final int schematicHeight;

    public SkyblockIslandCreateRequest(Islander owner, File schematicLevel, int schematicHeight) {
        this.owner = owner;
        this.schematicLevel = schematicLevel;
        this.schematicHeight = schematicHeight;
    }

    public Islander getOwner() {
        return owner;
    }

    public File getSchematicFile() {
        return schematicLevel;
    }

    public int getSchematicHeight() {
        return schematicHeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkyblockIslandCreateRequest that = (SkyblockIslandCreateRequest) o;
        return schematicHeight == that.schematicHeight &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(schematicLevel, that.schematicLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, schematicLevel, schematicHeight);
    }
}
