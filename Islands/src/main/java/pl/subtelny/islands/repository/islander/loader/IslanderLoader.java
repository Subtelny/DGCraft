package pl.subtelny.islands.repository.islander.loader;

import org.jooq.Configuration;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.repository.island.anemia.IslandMemberAnemia;
import pl.subtelny.islands.repository.islander.anemia.IslanderAnemia;

import java.util.List;
import java.util.Optional;

public class IslanderLoader {

    private final Configuration configuration;

    public IslanderLoader(Configuration configuration) {
        this.configuration = configuration;
    }

    public Optional<Islander> loadIslander(AccountId accountId) {
        loadIslanderAnemia(accountId);

        Islander islander = new Islander(accountId);

        return Optional.empty();
    }

    private void loadIslanderAnemia(AccountId accountId) {
        IslanderLoadRequest request = IslanderLoadRequest.newBuilder()
                .where(accountId)
                .build();
        IslanderAnemiaLoadAction anemiaLoadAction = new IslanderAnemiaLoadAction(configuration, request);
        IslanderAnemia loadedData = anemiaLoadAction.perform();
    }

    private Optional<IslandId> findSkyblockIslandInList(List<IslandMemberAnemia> islandMemberAnemias) {
        return null;//islandMemberAnemias.stream().filter(islandMemberAnemia -> islandMemberAnemia.)
    }

}
