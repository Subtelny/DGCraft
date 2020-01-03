package pl.subtelny.islands.repository;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Optional;
import java.util.function.Function;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.guild.Guild;
import pl.subtelny.islands.model.guild.GuildId;
import pl.subtelny.repository.Storage;

@Component
public class GuildRepository extends Storage<GuildId, Optional<Guild>> {

	public GuildRepository() {
		super(Caffeine.newBuilder().build());
	}

	@Override
	public Function<? super GuildId, ? extends Optional<Guild>> computeData() {
		return null;
	}
}
