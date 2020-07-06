package pl.subtelny.islands.repository.islander.loader;

import com.google.common.collect.Lists;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.generated.tables.tables.Islanders;
import pl.subtelny.islands.model.islander.IslanderId;
import pl.subtelny.islands.repository.islander.anemia.IslanderAnemia;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.impl.DSL;
import pl.subtelny.islands.model.guild.GuildId;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.repository.LoadAction;

import java.util.List;
import java.util.Optional;

public class IslanderAnemiaLoadAction implements LoadAction<IslanderAnemia> {

    private final Configuration configuration;

    private final IslanderLoadRequest request;

    public IslanderAnemiaLoadAction(Configuration configuration, IslanderLoadRequest request) {
        this.configuration = configuration;
        this.request = request;
    }

    @Override
    public IslanderAnemia perform() {
        DSL.using(configuration)
                .select()
                .from(Islanders.ISLANDERS)
                .where(whereConditions());

        return null;
    }

    @Override
    public List<IslanderAnemia> performList() {
        return null;
    }

    private List<Condition> whereConditions() {
        List<Condition> conditions = Lists.newArrayList();
        Optional<IslanderId> islanderIdOpt = request.getIslanderId();
        islanderIdOpt.ifPresent(islanderId -> conditions.add(Islanders.ISLANDERS.ID.eq(islanderId.getId())));

        Optional<GuildId> guildIdOpt = request.getGuildId();
        guildIdOpt.ifPresent(guildId -> conditions.add(Islanders.ISLANDERS.GUILD.eq(guildId.getId())));

        Optional<IslandId> skyblockIslandIdOpt = request.getSkyblockIslandId();
        return conditions;
    }
}
