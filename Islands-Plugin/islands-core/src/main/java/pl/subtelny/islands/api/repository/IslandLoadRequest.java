package pl.subtelny.islands.api.repository;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang.Validate;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandType;

public class IslandLoadRequest {

	private final IslandId islandId;

	private final IslandType islandType;

	public IslandLoadRequest(IslandId islandId, IslandType islandType) {
		Validate.notNull(islandType, "IslandType cannot be null");
		this.islandId = islandId;
		this.islandType = islandType;
	}

	public Optional<IslandId> getIslandId() {
		return Optional.ofNullable(islandId);
	}

	public IslandType getIslandType() {
		return islandType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		IslandLoadRequest that = (IslandLoadRequest) o;
		return Objects.equals(islandId, that.islandId) &&
				Objects.equals(islandType, that.islandType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(islandId, islandType);
	}
}
