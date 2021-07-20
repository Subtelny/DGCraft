package pl.subtelny.islands.api.configuration;

import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;

import java.util.List;
import java.util.Objects;

public class IslandExtendLevel implements Comparable<IslandExtendLevel> {

    private final int size;

    private final List<Condition> conditions;

    private final List<CostCondition> costConditions;

    public IslandExtendLevel(int size, List<Condition> conditions, List<CostCondition> costConditions) {
        Validation.isTrue(size > 0, "Extend level should have size higher than 0");
        this.size = size;
        this.costConditions = costConditions;
        this.conditions = conditions;
    }

    public int getSize() {
        return size;
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
        IslandExtendLevel that = (IslandExtendLevel) o;
        return size == that.size &&
                Objects.equals(conditions, that.conditions) &&
                Objects.equals(costConditions, that.costConditions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, conditions, costConditions);
    }

    @Override
    public int compareTo(IslandExtendLevel islandExtendLevel) {
        return Integer.compare(size, islandExtendLevel.size);
    }
}
