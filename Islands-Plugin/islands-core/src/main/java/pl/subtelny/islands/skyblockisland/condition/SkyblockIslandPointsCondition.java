package pl.subtelny.islands.skyblockisland.condition;

import org.bukkit.entity.Player;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.model.islander.IslanderId;
import pl.subtelny.islands.repository.islander.IslanderRepository;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.messages.MessageKey;

import java.util.Objects;
import java.util.Optional;

public class SkyblockIslandPointsCondition implements Condition {

    private final IslanderRepository islanderRepository;

    private final int points;

    public SkyblockIslandPointsCondition(IslanderRepository islanderRepository, int points) {
        this.islanderRepository = islanderRepository;
        this.points = points;
    }

    @Override
    public boolean satisfiesCondition(Player player) {
        Optional<Islander> islanderOpt = islanderRepository.getIslanderIfPresent(IslanderId.of(player.getUniqueId()));
        return islanderOpt.
                flatMap(Islander::getSkyblockIsland)
                .filter(skyblockIsland -> skyblockIsland.getPoints() >= points).isPresent();
    }

    @Override
    public MessageKey getMessageKey() {
        return new MessageKey("condition.skyblockIslandPoints.not_satisfied", points);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkyblockIslandPointsCondition that = (SkyblockIslandPointsCondition) o;
        return points == that.points;
    }

    @Override
    public int hashCode() {
        return Objects.hash(points);
    }
}
