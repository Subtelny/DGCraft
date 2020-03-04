package pl.subtelny.islands.model.island;

public class IslandCoordinates {

	private final int x;

	private final int z;

	public IslandCoordinates(int x, int z) {
		this.x = x;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}
}
