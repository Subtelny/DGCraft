package pl.subtelny.islands.repository.loader.island;

import com.google.common.collect.Lists;
import java.util.List;
import org.jooq.Condition;
import pl.subtelny.core.generated.tables.SkyblockIslands;
import pl.subtelny.islands.model.island.IslandCoordinates;
import pl.subtelny.islands.model.island.IslandId;

public class IslandAnemiaLoaderRequest {

	private final List<Condition> where;

	public IslandAnemiaLoaderRequest(List<Condition> where) {
		this.where = where;
	}

	public List<Condition> getWhere() {
		return where;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {

		private List<Condition> where = Lists.newArrayList();

		private Builder() { }

		public Builder where(IslandCoordinates islandCoordinates) {
			Condition islandCoords = SkyblockIslands.SKYBLOCK_ISLANDS.X.eq(islandCoordinates.getX())
					.and(SkyblockIslands.SKYBLOCK_ISLANDS.Z.eq(islandCoordinates.getZ()));
			where.add(islandCoords);
			return this;
		}

		public Builder where(IslandId islandId) {
			Condition islandIdentity = SkyblockIslands.SKYBLOCK_ISLANDS.ISLAND_ID.eq(islandId.getId().intValue());
			where.add(islandIdentity);
			return this;
		}

		public IslandAnemiaLoaderRequest build() {
			return new IslandAnemiaLoaderRequest(where);
		}

	}
}
