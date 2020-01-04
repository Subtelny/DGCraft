package pl.subtelny.islands.storage;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Optional;
import java.util.function.Function;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.guild.GuildIsland;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.IslandRepository;
import pl.subtelny.islands.repository.loader.island.GuildIslandAnemia;
import pl.subtelny.islands.repository.loader.island.IslandAnemia;
import pl.subtelny.islands.repository.loader.island.SkyblockIslandAnemia;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import pl.subtelny.repository.Storage;
import pl.subtelny.utils.cuboid.Cuboid;

@Component
public class IslandStorage extends Storage<IslandId, Optional<Island>> {

	private final IslandRepository islandRepository;

	@Autowired
	public IslandStorage(IslandRepository islandRepository) {
		super(Caffeine.newBuilder().build());
		this.islandRepository = islandRepository;
	}

	@Override
	protected Function<? super IslandId, ? extends Optional<Island>> computeData() {
		return islandId -> {
			Optional<IslandAnemia> island = islandRepository.findIsland(islandId);
			return island.map(IslandMapper::map);
		};
	}

	private static class IslandMapper {

		public static Island map(IslandAnemia islandData) {
			IslandType type = islandData.getIslandType();
			Island island;
			if (type == IslandType.SKYBLOCK) {
				island = mapAsSkyblock((SkyblockIslandAnemia) islandData);
			} else {
				island = mapAsGuild((GuildIslandAnemia) islandData);
			}
			return island;
		}

		private static GuildIsland mapAsGuild(GuildIslandAnemia data) {
			return new GuildIsland(data, data.getCuboid());
		}

		private static SkyblockIsland mapAsSkyblock(SkyblockIslandAnemia data) {
			Cuboid cuboid = SkyblockIslandUtil.defaultCuboid(data.getIslandCoordinates());
			return new SkyblockIsland(data, data.getIslandCoordinates(), cuboid);
		}

	}
}
