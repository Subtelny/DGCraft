package pl.subtelny.islands.islander.model;

import pl.subtelny.utilities.identity.CompoundIdentity;

public class IslandId extends CompoundIdentity {

	private static final int ISLAND_TYPE_POSITOIN = 0;

	private static final int ISLAND_ID = 0;

	public IslandId(IslandType islandType, Integer id) {
		super(values(islandType, id));
	}

	public static IslandId of(IslandType islandType, Integer id) {
		return new IslandId(islandType, id);
	}

	public Integer getId() {
		return Integer.parseInt(getAtPosition(ISLAND_ID));
	}

	public IslandType getIslandType() {
		return IslandType.valueOf(getAtPosition(ISLAND_TYPE_POSITOIN));
	}


}
