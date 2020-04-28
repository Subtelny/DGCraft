package pl.subtelny.islands.repository.island.loader;

import java.util.Optional;

import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SkyblockIslandLoadRequest extends IslandLoadRequest {

	private final IslandCoordinates islandCoordinates;

	public SkyblockIslandLoadRequest(IslandId islandId, IslandCoordinates islandCoordinates) {
		super(islandId);
		this.islandCoordinates = islandCoordinates;
	}

	public Optional<IslandCoordinates> getIslandCoordinates() {
		return Optional.ofNullable(islandCoordinates);
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {

		private IslandCoordinates islandCoordinates;

		private IslandId islandId;

		private Builder() { }

		public Builder where(IslandCoordinates islandCoordinates) {
			this.islandCoordinates = islandCoordinates;
			return this;
		}

		public Builder where(IslandId islandId) {
			this.islandId = islandId;
			return this;
		}

		public SkyblockIslandLoadRequest build() {
			return new SkyblockIslandLoadRequest(islandId, islandCoordinates);
		}

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SkyblockIslandLoadRequest that = (SkyblockIslandLoadRequest) o;

		return new EqualsBuilder()
				.appendSuper(super.equals(o))
				.append(islandCoordinates, that.islandCoordinates)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.appendSuper(super.hashCode())
				.append(islandCoordinates)
				.toHashCode();
	}
}