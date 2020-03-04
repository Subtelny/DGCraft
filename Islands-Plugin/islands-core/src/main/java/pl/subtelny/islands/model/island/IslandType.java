package pl.subtelny.islands.model.island;

public enum IslandType {

	SKYBLOCK,
	GUILD;

	public boolean isSkyblock() {
		return this == SKYBLOCK;
	}

}
