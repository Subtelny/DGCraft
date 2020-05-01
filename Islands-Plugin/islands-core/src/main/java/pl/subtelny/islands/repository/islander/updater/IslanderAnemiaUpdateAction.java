package pl.subtelny.islands.repository.islander.updater;

import org.jooq.Configuration;
import org.jooq.impl.DSL;
import pl.subtelny.generated.tables.tables.Islanders;
import pl.subtelny.generated.tables.tables.records.IslandersRecord;
import pl.subtelny.islands.model.guild.GuildId;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.repository.islander.anemia.IslanderAnemia;
import pl.subtelny.repository.UpdateAction;

import java.util.concurrent.CompletionStage;

public class IslanderAnemiaUpdateAction implements UpdateAction<IslanderAnemia> {

    private final Configuration configuration;

    public IslanderAnemiaUpdateAction(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void perform(IslanderAnemia islanderAnemia) {
        IslandersRecord islandersRecord = new IslandersRecord();
        islandersRecord.setId(islanderAnemia.getAccountId().getId());

        GuildId guildId = islanderAnemia.getGuildId();
        islandersRecord.setGuild(guildId == null ? null : guildId.getId());

        IslandId islandId = islanderAnemia.getSkyblockIslandId();
        islandersRecord.setSkyblockIsland(guildId == null ? null : islandId.getId());

        DSL.using(configuration)
                .insertInto(Islanders.ISLANDERS)
                .set(islandersRecord)
                .onDuplicateKeyUpdate()
                .set(islandersRecord)
                .execute();
    }

    @Override
    public CompletionStage<Integer> performAsync(IslanderAnemia islanderAnemia) {
        return null;
    }
}
