package pl.subtelny.islands.model;

import pl.subtelny.utils.cuboid.Cuboid;

public class GuildIsland extends Island {

    private GuildId guildId;

    public GuildIsland(IslandId islandId, Cuboid cuboid, GuildId guildId) {
        super(islandId, cuboid);
        this.guildId = guildId;
    }

    public GuildId getGuildId() {
        return guildId;
    }
}
