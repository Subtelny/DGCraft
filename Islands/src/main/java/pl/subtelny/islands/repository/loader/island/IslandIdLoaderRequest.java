package pl.subtelny.islands.repository.loader.island;

import com.google.common.collect.Lists;
import org.jooq.Condition;
import pl.subtelny.core.generated.enums.Islandmembertype;
import pl.subtelny.core.generated.tables.GuildIslands;
import pl.subtelny.core.generated.tables.IslandMembers;
import pl.subtelny.core.generated.tables.SkyblockIslands;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.guild.GuildId;
import pl.subtelny.islands.model.island.IslandCoordinates;

import java.util.List;
import java.util.UUID;

public class IslandIdLoaderRequest {

    private final List<Condition> where;

    private final RequestType requestType;

    public IslandIdLoaderRequest(List<Condition> where, RequestType requestType) {
        this.where = where;
        this.requestType = requestType;
    }

    public List<Condition> getWhere() {
        return where;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public static SkyblockBuilder newSkyblockBuilder() {
        return new SkyblockBuilder();
    }

    public static IslandMemberBuilder newIslandMemberBuilder() {
        return new IslandMemberBuilder();
    }

    public static GuildBuilder newGuildBuilder() {
        return new GuildBuilder();
    }

    public static class IslandBuilder {

        protected List<Condition> where = Lists.newArrayList();

        public List<Condition> getWhere() {
            return where;
        }

    }

    public static class IslandMemberBuilder extends IslandBuilder {

        private IslandMemberBuilder() {
        }

        public IslandMemberBuilder where(AccountId accountId) {
            where.add(IslandMembers.ISLAND_MEMBERS.ID.eq(accountId.getId().toString())
                    .and(IslandMembers.ISLAND_MEMBERS.MEMBER_TYPE.eq(Islandmembertype.ISLANDER)));
            return this;
        }

        public IslandMemberBuilder where(GuildId guildId) {
            where.add(IslandMembers.ISLAND_MEMBERS.ID.eq(guildId.getId().toString())
                    .and(IslandMembers.ISLAND_MEMBERS.MEMBER_TYPE.eq(Islandmembertype.GUILD)));
            return this;
        }

        public IslandIdLoaderRequest build() {
            return new IslandIdLoaderRequest(where, RequestType.SEARCH_ISLAND_MEMBER);
        }

    }

    public static class GuildBuilder extends IslandBuilder {

        private GuildBuilder() {
        }

        public GuildBuilder where(GuildId guildId) {
            where.add(GuildIslands.GUILD_ISLANDS.OWNER.eq(guildId.getId()));
            return this;
        }

        public IslandIdLoaderRequest build() {
            return new IslandIdLoaderRequest(where, RequestType.SEARCH_GUILD_ISLAND);
        }

    }

    public static class SkyblockBuilder extends IslandBuilder {

        private SkyblockBuilder() {
        }

        public SkyblockBuilder where(IslandCoordinates islandCoordinates) {
            int x = islandCoordinates.getX();
            int z = islandCoordinates.getZ();
            Condition condition = SkyblockIslands.SKYBLOCK_ISLANDS.X.eq(x)
                    .and(SkyblockIslands.SKYBLOCK_ISLANDS.Z.eq(z));
            where.add(condition);
            return this;
        }

        public SkyblockBuilder where(UUID owner) {
            where.add(SkyblockIslands.SKYBLOCK_ISLANDS.OWNER.eq(owner));
            return this;
        }

        public IslandIdLoaderRequest build() {
            return new IslandIdLoaderRequest(where, RequestType.SEARCH_SKYBLOCK_ISLAND);
        }
    }

    public enum RequestType {

        SEARCH_ISLAND_MEMBER,
        SEARCH_SKYBLOCK_ISLAND,
        SEARCH_GUILD_ISLAND

    }

}
