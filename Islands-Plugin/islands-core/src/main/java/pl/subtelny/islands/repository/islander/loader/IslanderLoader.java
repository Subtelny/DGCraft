package pl.subtelny.islands.repository.islander.loader;

import pl.subtelny.islands.model.islander.Islander;
import org.jooq.Configuration;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.islands.model.guild.GuildId;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.islander.IslanderId;
import pl.subtelny.islands.repository.islander.anemia.IslanderAnemia;

import java.util.Optional;

public class IslanderLoader {

	private final Configuration configuration;

	public IslanderLoader(Configuration configuration) {
		this.configuration = configuration;
	}

	public Optional<Islander> loadIslander(IslanderLoadRequest request) {
		Optional<IslanderAnemia> anemiaOpt = performAction(request);
		if (anemiaOpt.isPresent()) {
			Islander islander = mapAnemiaIntoDomain(anemiaOpt.get());
			return Optional.of(islander);
		}
		return Optional.empty();
	}

	private Optional<IslanderAnemia> performAction(IslanderLoadRequest request) {
		IslanderAnemiaLoadAction action = new IslanderAnemiaLoadAction(configuration, request);
		return Optional.ofNullable(action.perform());
	}

	private Islander mapAnemiaIntoDomain(IslanderAnemia anemia) {
		IslanderId islanderId = anemia.getIslanderId();
		GuildId guildId = anemia.getGuildId();
		IslandId skyblockIslandId = anemia.getSkyblockIslandId();
		return new Islander(islanderId, skyblockIslandId, guildId);
	}

}
