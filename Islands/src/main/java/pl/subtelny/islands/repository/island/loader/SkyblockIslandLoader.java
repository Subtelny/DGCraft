package pl.subtelny.islands.repository.island.loader;

import java.util.Optional;
import org.jooq.Configuration;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.island.SkyblockIslandAnemia;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import pl.subtelny.utils.cuboid.Cuboid;

public class SkyblockIslandLoader extends IslandLoader<SkyblockIslandAnemia, SkyblockIsland> {

	private final Configuration configuration;

	public SkyblockIslandLoader(Configuration configuration) {
		this.configuration = configuration;
	}

	public Optional<SkyblockIsland> loadIsland(SkyblockIslandLoadRequest request) {
		SkyblockIslandAnemiaLoadAction loader = new SkyblockIslandAnemiaLoadAction(configuration, request);
		return loadIsland(loader);
	}

	@Override
	protected SkyblockIsland mapAnemiaToDomain(SkyblockIslandAnemia anemia) {
		Cuboid cuboid = SkyblockIslandUtil.defaultCuboid(anemia.getIslandCoordinates());
		return new SkyblockIsland(anemia, anemia.getIslandCoordinates(), cuboid);
	}
}
