package pl.subtelny.islands.repository.island.loader;

import java.util.Optional;

import pl.subtelny.islands.model.island.IslandId;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class IslandLoadRequest {

	private final IslandId islandId;

	public IslandLoadRequest(IslandId islandId) {
		this.islandId = islandId;
	}

	public Optional<IslandId> getIslandId() {
		return Optional.ofNullable(islandId);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		IslandLoadRequest request = (IslandLoadRequest) o;

		return new EqualsBuilder()
				.append(islandId, request.islandId)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(islandId)
				.toHashCode();
	}
}
