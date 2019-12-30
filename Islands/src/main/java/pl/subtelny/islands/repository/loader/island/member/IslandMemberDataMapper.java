package pl.subtelny.islands.repository.loader.island.member;

import org.jooq.Record;
import org.jooq.RecordMapper;
import pl.subtelny.islands.generated.tables.IslandMembers;
import pl.subtelny.islands.model.IslandMemberType;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.repository.loader.island.member.IslandMemberData;

class IslandMemberDataMapper implements RecordMapper<Record, IslandMemberData> {

	@Override
	public IslandMemberData map(Record record) {
		IslandId islandId = IslandId.of(record.get(IslandMembers.ISLAND_MEMBERS.ISLAND_ID).longValue());
		String id = record.get(IslandMembers.ISLAND_MEMBERS.ID);
		String type = record.get(IslandMembers.ISLAND_MEMBERS.MEMBER_TYPE).getLiteral();
		IslandMemberType islandMemberType = IslandMemberType.valueOf(type);
		return new IslandMemberData(islandId, id, islandMemberType);
	}
}
