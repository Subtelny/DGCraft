package pl.subtelny.islands.repository.loader.island.member;

import com.google.common.collect.Lists;
import java.util.List;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.impl.DSL;
import pl.subtelny.core.generated.tables.IslandMembers;
import pl.subtelny.islands.model.IslandMemberType;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.repository.Loader;
import pl.subtelny.repository.LoaderResult;

public class IslandMemberAnemiaLoader extends Loader<IslandMemberAnemia> {

	private final Configuration configuration;

	private final IslandMemberAnemiaLoaderRequest request;

	public IslandMemberAnemiaLoader(Configuration configuration, IslandMemberAnemiaLoaderRequest request) {
		this.configuration = configuration;
		this.request = request;
	}

	@Override
	public LoaderResult<IslandMemberAnemia> perform() {
		List<IslandMemberAnemia> islandMembersData = Lists.newArrayList();
		islandMembersData.addAll(loadIslandMemberData());
		return new LoaderResult(islandMembersData);
	}

	private List<IslandMemberAnemia> loadIslandMemberData() {
		return DSL.using(this.configuration)
				.select()
				.from(IslandMembers.ISLAND_MEMBERS)
				.where(request.getWhere())
				.fetch(this::mapToIslandMemberData);
	}

	private IslandMemberAnemia mapToIslandMemberData(Record record) {
		IslandId islandId = IslandId.of(record.get(IslandMembers.ISLAND_MEMBERS.ISLAND_ID).longValue());
		String id = record.get(IslandMembers.ISLAND_MEMBERS.ID);
		IslandMemberType type = IslandMemberType.valueOf(record.get(IslandMembers.ISLAND_MEMBERS.MEMBER_TYPE).getLiteral());
		return new IslandMemberAnemia(islandId, id, type);
	}

}
