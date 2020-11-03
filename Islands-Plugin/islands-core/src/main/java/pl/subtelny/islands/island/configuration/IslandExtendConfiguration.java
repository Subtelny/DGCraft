package pl.subtelny.islands.island.configuration;

import pl.subtelny.utilities.Validation;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class IslandExtendConfiguration {

    private final List<IslandExtendLevel> levels;

    public IslandExtendConfiguration(List<IslandExtendLevel> levels) {
        Validation.isTrue(levels != null, "Extend levels map cannot be null");
        this.levels = sortLevels(levels);
    }

    public Optional<Integer> getTotalSize() {
        return levels.stream()
                .max(IslandExtendLevel::compareTo)
                .map(IslandExtendLevel::getSize);
    }

    public int getLevelsCount() {
        return levels.size();
    }

    public List<IslandExtendLevel> getLevels() {
        return levels;
    }

    private List<IslandExtendLevel> sortLevels(List<IslandExtendLevel> levels) {
        levels.sort(IslandExtendLevel::compareTo);
        return levels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandExtendConfiguration that = (IslandExtendConfiguration) o;
        return Objects.equals(levels, that.levels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(levels);
    }
}
