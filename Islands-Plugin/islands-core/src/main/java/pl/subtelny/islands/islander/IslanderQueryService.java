package pl.subtelny.islands.islander;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.*;
import pl.subtelny.islands.island.membership.IslandMemberLoader;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.repository.IslanderRepository;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class IslanderQueryService extends IslanderService implements IslandMemberLoader<Islander> {

    private final IslanderRepository islanderRepository;

    @Autowired
    public IslanderQueryService(IslanderRepository islanderRepository) {
        this.islanderRepository = islanderRepository;
    }

    public List<Islander> getIslanders(List<IslanderId> islanderIds) {
        return islanderIds.stream()
                .map(islanderRepository::findIslander)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Islander getIslander(Player player) {
        IslanderId islanderId = getIslanderId(player);
        return islanderRepository
                .getIslanderIfPresent(islanderId)
                .orElseThrow(() -> ValidationException.of("islander.not_found", player.getName()));
    }

    @Override
    public Optional<Islander> findIslandMember(IslandMemberId islandMemberId) {
        IslanderId islanderId = toIslanderId(islandMemberId);
        return islanderRepository
                .getIslanderIfPresent(islanderId);
    }

    @Override
    public List<Islander> getIslandMembers(List<IslandMemberId> islandMemberIds) {
        return islandMemberIds.stream()
                .map(this::toIslanderId)
                .map(islanderRepository::findIslander)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public IslandMemberType getType() {
        return Islander.ISLAND_MEMBER_TYPE;
    }

    private IslanderId toIslanderId(IslandMemberId islandMemberId) {
        String value = islandMemberId.getValue();
        UUID uuid = UUID.fromString(value);
        return IslanderId.of(uuid);
    }
}
