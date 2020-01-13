package pl.subtelny.islands.model;

public enum IslandType {

	SKYBLOCK,
	GUILD;

	public boolean isSkyblock() {
		return this == SKYBLOCK;
	}

}
