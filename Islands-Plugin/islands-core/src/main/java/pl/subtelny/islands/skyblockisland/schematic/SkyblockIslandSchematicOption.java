package pl.subtelny.islands.skyblockisland.schematic;

import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;

import java.util.List;
import java.util.Objects;

public class SkyblockIslandSchematicOption {

    private final String schematicFilePath;

    private final String name;

    private final List<String> lore;

    private final boolean defaultSchematic;

    private final List<Condition> conditions;

    private final List<CostCondition> costConditions;

    public SkyblockIslandSchematicOption(String schematicFilePath, String name, List<String> lore, boolean defaultSchematic, List<Condition> conditions, List<CostCondition> costConditions) {
        this.schematicFilePath = schematicFilePath;
        this.name = name;
        this.lore = lore;
        this.defaultSchematic = defaultSchematic;
        this.conditions = conditions;
        this.costConditions = costConditions;
    }

    public String getSchematicFilePath() {
        return schematicFilePath;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public List<CostCondition> getCostConditions() {
        return costConditions;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }

    public boolean isDefaultSchematic() {
        return defaultSchematic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkyblockIslandSchematicOption that = (SkyblockIslandSchematicOption) o;
        return defaultSchematic == that.defaultSchematic &&
                Objects.equals(schematicFilePath, that.schematicFilePath) &&
                Objects.equals(name, that.name) &&
                Objects.equals(lore, that.lore) &&
                Objects.equals(conditions, that.conditions) &&
                Objects.equals(costConditions, that.costConditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schematicFilePath, name, lore, defaultSchematic, conditions, costConditions);
    }
}
