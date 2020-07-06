package pl.subtelny.islands.skyblockisland.extendcuboid;

import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import java.util.List;
import java.util.Objects;

public class SkyblockIslandExtendCuboidLevel {

    private final int islandSize;

    private final List<Condition> conditions;

    private final List<CostCondition> costConditions;

    public SkyblockIslandExtendCuboidLevel(int islandSize, List<Condition> conditions, List<CostCondition> costConditions) {
        this.islandSize = islandSize;
        this.conditions = conditions;
        this.costConditions = costConditions;
    }

    public int getIslandSize() {
        return islandSize;
    }

    public List<CostCondition> getCostConditions() {
        return costConditions;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkyblockIslandExtendCuboidLevel that = (SkyblockIslandExtendCuboidLevel) o;
        return islandSize == that.islandSize &&
                Objects.equals(conditions, that.conditions) &&
                Objects.equals(costConditions, that.costConditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(islandSize, conditions, costConditions);
    }
}
