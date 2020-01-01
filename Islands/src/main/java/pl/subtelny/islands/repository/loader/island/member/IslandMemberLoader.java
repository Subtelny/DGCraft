package pl.subtelny.islands.repository.loader.island.member;

import org.jooq.Configuration;
import pl.subtelny.islands.repository.loader.Loader;

public class IslandMemberLoader extends Loader<IslandMemberLoaderResult> {

	private final Configuration configuration;

	private final IslandMemberLoaderRequest request;

	public IslandMemberLoader(Configuration configuration, IslandMemberLoaderRequest request) {
		this.configuration = configuration;
		this.request = request;
	}

	@Override
	public IslandMemberLoaderResult perform() {
		return null;
	}
}
