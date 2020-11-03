package pl.subtelny.islands.island.configuration;

import java.util.List;
import java.util.Objects;

public class IslandSchematicConfiguration {

    private final List<IslandSchematicLevel> schematicLevels;

    public IslandSchematicConfiguration(List<IslandSchematicLevel> schematicLevels) {
        this.schematicLevels = schematicLevels;
    }

    public List<IslandSchematicLevel> getSchematicLevels() {
        return schematicLevels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandSchematicConfiguration that = (IslandSchematicConfiguration) o;
        return Objects.equals(schematicLevels, that.schematicLevels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schematicLevels);
    }
}
