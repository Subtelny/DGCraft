package pl.subtelny.islands.islander;

import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.islands.island.IslandMember;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.IslandMemberType;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.island.membership.IslandMemberLoader;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.repository.IslanderRepository;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class IslanderQueryService extends IslanderService implements IslandMemberLoader {

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
    public Optional<IslandMember> findIslandMember(IslandMemberId islandMemberId) {
        IslanderId islanderId = toIslanderId(islandMemberId);
        return islanderRepository
                .getIslanderIfPresent(islanderId)
                .map(islander -> islander);
    }

    @Override
    public List<IslandMember> getIslandMembers(List<IslandMemberId> islandMemberIds) {
        return islandMemberIds.stream()
                .map(this::toIslanderId)
                .map(islanderRepository::findIslander)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public IslandMemberType getType() {
        return Islander.TYPE;
    }

    private IslanderId toIslanderId(IslandMemberId islandMemberId) {
        String value = islandMemberId.getValue();
        UUID uuid = UUID.fromString(value);
        return IslanderId.of(uuid);
    }
}
