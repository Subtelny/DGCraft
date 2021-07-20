package pl.subtelny.islands.module.skyblock.repository.load;

import java.util.Objects;
import java.util.Optional;

import pl.subtelny.islands.module.skyblock.IslandCoordinates;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.repository.IslandLoadRequest;

public class SkyblockIslandLoadRequest extends IslandLoadRequest {

	private final IslandCoordinates islandCoordinates;

	public SkyblockIslandLoadRequest(IslandId islandId, IslandCoordinates islandCoordinates, IslandType islandType) {
		super(islandId, islandType);
		this.islandCoordinates = islandCoordinates;
	}

	public Optional<IslandCoordinates> getIslandCoordinates() {
		return Optional.ofNullable(islandCoordinates);
	}

	public static Builder newBuilder(IslandType islandType) {
		return new Builder(islandType);
	}

	public static class Builder {

		private IslandCoordinates islandCoordinates;

		private IslandId islandId;

		private final IslandType islandType;

		private Builder(IslandType islandType) {
			this.islandType = islandType;
		}

		public Builder where(IslandCoordinates islandCoordinates) {
			this.islandCoordinates = islandCoordinates;
			return this;
		}

		public Builder where(IslandId islandId) {
			this.islandId = islandId;
			return this;
		}

		public SkyblockIslandLoadRequest build() {
			return new SkyblockIslandLoadRequest(islandId, islandCoordinates, islandType);
		}

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		SkyblockIslandLoadRequest that = (SkyblockIslandLoadRequest) o;
		return Objects.equals(islandCoordinates, that.islandCoordinates);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), islandCoordinates);
	}
}
