package pl.subtelny.islands.island.configuration;

import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;

import java.util.List;
import java.util.Objects;

public class IslandSchematicLevel {

    private final String schematicFileName;

    private final List<Condition> conditions;

    private final List<CostCondition> costConditions;

    public IslandSchematicLevel(String schematicFileName, List<Condition> conditions, List<CostCondition> costConditions) {
        this.schematicFileName = schematicFileName;
        this.conditions = conditions;
        this.costConditions = costConditions;
    }

    public String getSchematicFileName() {
        return schematicFileName;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public List<CostCondition> getCostConditions() {
        return costConditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandSchematicLevel that = (IslandSchematicLevel) o;
        return Objects.equals(schematicFileName, that.schematicFileName) &&
                Objects.equals(conditions, that.conditions) &&
                Objects.equals(costConditions, that.costConditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schematicFileName, conditions, costConditions);
    }

}
