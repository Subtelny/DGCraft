package pl.subtelny.islands.island;

import pl.subtelny.utilities.identity.CompoundIdentity;

public class IslandId extends CompoundIdentity {

	private static final int ID_POSITION = 0;

	private static final int ISLAND_TYPE_POSITION = 1;

    private IslandId(String values) {
        super(values);
    }

    public IslandId(Integer id, IslandType islandType) {
        super(id + "@" + islandType.getInternal());
    }

    public static IslandId of(Integer id, IslandType islandType) {
        return new IslandId(id, islandType);
    }

    public static IslandId of(String internal) {
        return new IslandId(internal);
    }

    public Integer getId() {
    	return Integer.valueOf(getAtPosition(ID_POSITION));
	}

	public IslandType getIslandType() {
    	return IslandType.of(getAtPosition(ISLAND_TYPE_POSITION));
	}

}
