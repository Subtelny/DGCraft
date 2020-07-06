package pl.subtelny.islands.skyblockisland.extendcuboid;

import org.bukkit.entity.Player;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;

import java.util.Objects;
import java.util.Optional;

public class SkyblockIslandExtendCuboidRequest {

    private final Player player;

    private final SkyblockIsland skyblockIsland;

    private final int extendLevel;

    private final boolean skipConditions;

    private SkyblockIslandExtendCuboidRequest(Player player, SkyblockIsland skyblockIsland, int extendLevel, boolean skipConditions) {
        this.player = player;
        this.skyblockIsland = skyblockIsland;
        this.extendLevel = extendLevel;
        this.skipConditions = skipConditions;
    }

    public Optional<Player> getPlayer() {
        return Optional.ofNullable(player);
    }

    public SkyblockIsland getSkyblockIsland() {
        return skyblockIsland;
    }

    public boolean isSkipConditions() {
        return skipConditions;
    }

    public int getExtendLevel() {
        return extendLevel;
    }

    public static class Builder {

        private final SkyblockIsland skyblockIsland;

        private final int extendLevel;

        private Player player;

        private boolean skipConditions;

        public Builder(SkyblockIsland skyblockIsland, int extendLevel) {
            this.skyblockIsland = skyblockIsland;
            this.extendLevel = extendLevel;
        }

        public Builder player(Player player) {
            this.player = player;
            return this;
        }

        public Builder skipConditions(boolean skipConditions) {
            this.skipConditions = skipConditions;
            return this;
        }

        public SkyblockIslandExtendCuboidRequest build() {
            return new SkyblockIslandExtendCuboidRequest(player, skyblockIsland, extendLevel, skipConditions);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkyblockIslandExtendCuboidRequest that = (SkyblockIslandExtendCuboidRequest) o;
        return extendLevel == that.extendLevel &&
                skipConditions == that.skipConditions &&
                Objects.equals(player, that.player) &&
                Objects.equals(skyblockIsland, that.skyblockIsland);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, skyblockIsland, extendLevel, skipConditions);
    }
}
