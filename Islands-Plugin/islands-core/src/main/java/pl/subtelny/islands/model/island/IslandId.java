package pl.subtelny.islands.model.island;

import pl.subtelny.utilities.identity.CompoundIdentity;

public class IslandId extends CompoundIdentity {

	private static final int ISLAND_TYPE_POSITOIN = 0;

	private static final int ISLAND_ID = 0;

	public IslandId(IslandType islandType, Integer id) {
		super(values(islandType, id));
	}

	public int getId() {
		return Integer.parseInt(getAtPosition(ISLAND_ID));
	}

	public IslandType getIslandType() {
		return IslandType.valueOf(getAtPosition(ISLAND_TYPE_POSITOIN));
	}


}
