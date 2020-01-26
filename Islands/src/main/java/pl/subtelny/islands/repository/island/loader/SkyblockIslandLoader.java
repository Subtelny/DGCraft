package pl.subtelny.islands.repository.island.loader;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jooq.Configuration;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.IslandMemberType;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.island.anemia.IslandMemberAnemia;
import pl.subtelny.islands.repository.island.anemia.SkyblockIslandAnemia;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import pl.subtelny.utils.cuboid.Cuboid;

public class SkyblockIslandLoader extends IslandLoader<SkyblockIslandAnemia, SkyblockIsland> {

	public SkyblockIslandLoader(Configuration configuration) {
		super(configuration);
	}

	public Optional<SkyblockIsland> loadIsland(SkyblockIslandLoadRequest request) {
		SkyblockIslandAnemiaLoadAction loader = new SkyblockIslandAnemiaLoadAction(configuration, request);
		return loadIsland(loader);
	}

	@Override
	protected SkyblockIsland mapAnemiaToDomain(SkyblockIslandAnemia anemia,
			List<IslandMemberAnemia> islandMembers) {

		Set<AccountId> members = islandMembers.stream()
				.filter(memberAnemia -> memberAnemia.getIslandMemberType() == IslandMemberType.ISLANDER)
				.map(memberAnemia -> AccountId.of(UUID.fromString(memberAnemia.getId())))
				.collect(Collectors.toSet());

		Cuboid cuboid = SkyblockIslandUtil.defaultCuboid(anemia.getIslandCoordinates());
		return new SkyblockIsland(anemia, cuboid, members);
	}
}
