package pl.subtelny.islands.islander.model;

public enum IslandType {

	SKYBLOCK,
	GUILD;

	public boolean isSkyblock() {
		return this == SKYBLOCK;
	}

}
