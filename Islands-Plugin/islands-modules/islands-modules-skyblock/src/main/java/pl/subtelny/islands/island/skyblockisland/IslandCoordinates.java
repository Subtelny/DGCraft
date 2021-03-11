package pl.subtelny.islands.island.skyblockisland;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		IslandCoordinates that = (IslandCoordinates) o;
		return x == that.x &&
				z == that.z;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, z);
	}
}
