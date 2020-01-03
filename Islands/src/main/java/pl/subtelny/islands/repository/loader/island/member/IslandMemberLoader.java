package pl.subtelny.islands.repository.loader.island.member;

import com.google.common.collect.Lists;
import java.util.List;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.impl.DSL;
import pl.subtelny.islands.generated.tables.IslandMembers;
import pl.subtelny.islands.model.IslandMemberType;
import pl.subtelny.islands.model.island.IslandId;
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
		List<IslandMemberData> islandMembersData = Lists.newArrayList();
		islandMembersData.addAll(loadIslandMemberData());
		return new IslandMemberLoaderResult(islandMembersData);
	}

	private List<IslandMemberData> loadIslandMemberData() {
		return DSL.using(this.configuration)
				.select()
				.from(IslandMembers.ISLAND_MEMBERS)
				.where(request.getWhere())
				.fetch(this::mapToIslandMemberData);
	}

	private IslandMemberData mapToIslandMemberData(Record record) {
		IslandId islandId = IslandId.of(record.get(IslandMembers.ISLAND_MEMBERS.ISLAND_ID).longValue());
		String id = record.get(IslandMembers.ISLAND_MEMBERS.ID);
		IslandMemberType type = IslandMemberType.valueOf(record.get(IslandMembers.ISLAND_MEMBERS.MEMBER_TYPE).getLiteral());
		return new IslandMemberData(islandId, id, type);
	}

}
