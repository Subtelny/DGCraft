package pl.subtelny.islands.creator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.entity.Player;
import pl.subtelny.islands.model.island.IslandType;

public class SkyblockIslandCreateRequest extends IslandCreateRequest {

    private final String schematic;

    public SkyblockIslandCreateRequest(Player owner, String schematic) {
        super(owner);
        this.schematic = schematic;
    }

    public String getSchematic() {
        return schematic;
    }

    @Override
    public IslandType getIslandType() {
        return IslandType.SKYBLOCK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SkyblockIslandCreateRequest that = (SkyblockIslandCreateRequest) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(schematic, that.schematic)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(schematic)
                .toHashCode();
    }
}
