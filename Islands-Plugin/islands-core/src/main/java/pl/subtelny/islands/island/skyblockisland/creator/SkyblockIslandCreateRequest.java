package pl.subtelny.islands.island.skyblockisland.creator;

import pl.subtelny.islands.islander.model.Islander;

import java.io.File;
import java.util.Objects;

public class SkyblockIslandCreateRequest {

    private final Islander owner;

    private final File schematicLevel;

    public SkyblockIslandCreateRequest(Islander owner, File schematicLevel) {
        this.owner = owner;
        this.schematicLevel = schematicLevel;
    }

    public Islander getOwner() {
        return owner;
    }

    public File getSchematicFile() {
        return schematicLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkyblockIslandCreateRequest request = (SkyblockIslandCreateRequest) o;
        return Objects.equals(owner, request.owner) &&
                Objects.equals(schematicLevel, request.schematicLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, schematicLevel);
    }
}
