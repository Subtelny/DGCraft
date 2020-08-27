package pl.subtelny.islands.island;

import pl.subtelny.utilities.identity.CompoundIdentity;

public class IslandId extends CompoundIdentity {

	private static final int ISLAND_VALUE_POSITOIN = 0;

	private static final int ISLAND_ID_POSITION = 1;

	public IslandId(String value, Integer id) {
		super(CompoundIdentity.values(value, id));
	}

	public static IslandId of(String value, Integer id) {
		return new IslandId(value, id);
	}

	public Integer getId() {
		return Integer.parseInt(getAtPosition(ISLAND_ID_POSITION));
	}

	public String getValue() {
		return getAtPosition(ISLAND_VALUE_POSITOIN);
	}

}
