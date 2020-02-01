package pl.subtelny.islands.repository.islander;

import org.jooq.Configuration;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.repository.island.anemia.IslandMemberAnemia;

import java.util.List;
import java.util.Optional;

public class IslanderLoader {

    private final Configuration configuration;

    public IslanderLoader(Configuration configuration) {
        this.configuration = configuration;
    }

    public Optional<Islander> loadIslander(AccountId accountId) {

        return Optional.empty();
    }

    private Optional<IslandId> findSkyblockIslandInList(List<IslandMemberAnemia> islandMemberAnemias) {
        return null;//islandMemberAnemias.stream().filter(islandMemberAnemia -> islandMemberAnemia.)
    }

}
