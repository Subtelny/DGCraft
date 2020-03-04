package pl.subtelny.islands.creator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.entity.Player;
import pl.subtelny.islands.model.island.IslandType;

public abstract class IslandCreateRequest {

    private final Player creatingBy;

    public IslandCreateRequest(Player owner) {
        this.creatingBy = owner;
    }

    public Player getCreatingBy() {
        return creatingBy;
    }

    public abstract IslandType getIslandType();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		IslandCreateRequest that = (IslandCreateRequest) o;

		return new EqualsBuilder()
				.append(creatingBy, that.creatingBy)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(creatingBy)
				.toHashCode();
	}
}
