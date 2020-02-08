package pl.subtelny.islands.repository.islander.updater;

import org.jooq.Configuration;
import pl.subtelny.islands.model.guild.GuildId;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.repository.islander.anemia.IslanderAnemia;
import pl.subtelny.repository.Updater;

public class IslanderUpdater extends Updater<Islander> {

    private final Configuration configuration;

	public IslanderUpdater(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
    protected void performAction(Islander entity) {
        IslanderAnemiaUpdateAction action = new IslanderAnemiaUpdateAction(configuration);
        IslanderAnemia islanderAnemia = domainToAnemia(entity);
        action.perform(islanderAnemia);
    }

    private IslanderAnemia domainToAnemia(Islander islander) {
        IslanderAnemia islanderAnemia = new IslanderAnemia();
        islanderAnemia.setAccountId(islander.getAccount());

        GuildId guildId = islander.getGuild().orElse(null);
        islanderAnemia.setGuildId(guildId);

        IslandId islandId = islander.getSkyblockIsland().orElse(null);
        islanderAnemia.setSkyblockIslandId(islandId);
        return islanderAnemia;
    }

}
