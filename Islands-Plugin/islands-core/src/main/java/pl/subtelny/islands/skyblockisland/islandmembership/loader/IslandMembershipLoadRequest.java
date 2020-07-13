package pl.subtelny.islands.skyblockisland.islandmembership.loader;

import pl.subtelny.islands.islander.model.IslanderId;
import pl.subtelny.islands.islander.model.IslandId;

import java.util.Objects;
import java.util.Optional;

public class IslandMembershipLoadRequest {

	private final IslandId islandId;

	private final IslanderId islanderId;

	public IslandMembershipLoadRequest(IslandId islandId, IslanderId islanderId) {
		this.islandId = islandId;
		this.islanderId = islanderId;
	}

	public Optional<IslandId> getIslandId() {
		return Optional.ofNullable(islandId);
	}

	public Optional<IslanderId> getIslanderId() {
		return Optional.ofNullable(islanderId);
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {

		private IslandId islandId;

		private IslanderId islanderId;

		private Builder() { }

		public Builder where(IslandId islandId) {
			this.islandId = islandId;
			return this;
		}

		public Builder where(IslanderId islanderId) {
			this.islanderId = islanderId;
			return this;
		}

		public IslandMembershipLoadRequest build() {
			return new IslandMembershipLoadRequest(islandId, islanderId);
		}

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		IslandMembershipLoadRequest that = (IslandMembershipLoadRequest) o;
		return Objects.equals(islandId, that.islandId) &&
				Objects.equals(islanderId, that.islanderId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(islandId, islanderId);
	}
}
