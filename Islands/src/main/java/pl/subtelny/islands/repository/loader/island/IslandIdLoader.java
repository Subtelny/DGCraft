package pl.subtelny.islands.repository.loader.island;

import com.google.common.collect.Lists;
import java.util.List;
import org.jooq.Configuration;
import org.jooq.Record1;
import org.jooq.impl.DSL;
import pl.subtelny.core.generated.tables.GuildIslands;
import pl.subtelny.core.generated.tables.IslandMembers;
import pl.subtelny.core.generated.tables.Islands;
import pl.subtelny.core.generated.tables.SkyblockIslands;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.repository.Loader;
import pl.subtelny.repository.LoaderResult;

public class IslandIdLoader extends Loader<IslandId> {

	private final Configuration configuration;

	private final IslandIdLoaderRequest request;

	public IslandIdLoader(Configuration configuration, IslandIdLoaderRequest request) {
		this.configuration = configuration;
		this.request = request;
	}

	@Override
	public LoaderResult<IslandId> perform() {
		IslandIdLoaderRequest.RequestType requestType = request.getRequestType();

		List<IslandId> islandIds = Lists.newArrayList();
		if (requestType == IslandIdLoaderRequest.RequestType.SEARCH_ISLAND_MEMBER) {
			islandIds.addAll(loadByIslandMember());
		} else if (requestType == IslandIdLoaderRequest.RequestType.SEARCH_SKYBLOCK_ISLAND) {
			islandIds.addAll(loadBySkyblockIsland());
		} else {
			islandIds.addAll(loadByGuildIsland());
		}
		return new LoaderResult<>(islandIds);
	}

	private List<IslandId> loadByIslandMember() {
		return DSL.using(this.configuration)
				.select(IslandMembers.ISLAND_MEMBERS.ISLAND_ID)
				.from(IslandMembers.ISLAND_MEMBERS)
				.where(request.getWhere())
				.fetch(this::mapToIslandId);
	}

	private IslandId mapToIslandId(Record1<Integer> record) {
		return IslandId.of(Long.valueOf(record.value1()));
	}

	private List<IslandId> loadBySkyblockIsland() {
		return DSL.using(this.configuration)
				.select(Islands.ISLANDS.ID)
				.from(Islands.ISLANDS)
				.leftJoin(SkyblockIslands.SKYBLOCK_ISLANDS)
				.on(Islands.ISLANDS.ID.eq(SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID))
				.where(request.getWhere())
				.fetch(this::mapToIslandId);
	}

	private List<IslandId> loadByGuildIsland() {
		return DSL.using(this.configuration)
				.select(Islands.ISLANDS.ID)
				.from(Islands.ISLANDS)
				.leftJoin(GuildIslands.GUILD_ISLANDS)
				.on(Islands.ISLANDS.ID.eq(SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID))
				.where(request.getWhere())
				.fetch(this::mapToIslandId);
	}

}
