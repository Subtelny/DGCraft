package pl.subtelny.islands.repository.loader.island.member;

import com.google.common.collect.Lists;
import java.util.List;
import org.jooq.Condition;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.generated.enums.Islandmembertype;
import pl.subtelny.islands.generated.tables.IslandMembers;
import pl.subtelny.islands.model.guild.GuildId;

public class IslandMemberLoaderRequest {

	private final List<Condition> where;

	private IslandMemberLoaderRequest(List<Condition> where) {
		this.where = where;
	}

	public List<Condition> getWhere() {
		return where;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {

		private final List<Condition> where;

		private Builder() {
			this.where = Lists.newArrayList();
		}

		public Builder where(GuildId guildId) {
			where.add(IslandMembers.ISLAND_MEMBERS.ID.eq(guildId.getId().toString())
					.and(IslandMembers.ISLAND_MEMBERS.MEMBER_TYPE.eq(Islandmembertype.GUILD)));
			return this;
		}

		public Builder where(AccountId accountId) {
			where.add(IslandMembers.ISLAND_MEMBERS.ID.eq(accountId.getId().toString())
					.and(IslandMembers.ISLAND_MEMBERS.MEMBER_TYPE.eq(Islandmembertype.ISLANDER)));
			return this;
		}

		public IslandMemberLoaderRequest build() {
			return new IslandMemberLoaderRequest(where);
		}
	}

}
