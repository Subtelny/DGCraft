package pl.subtelny.islands.model.island;

import pl.subtelny.utilities.BasicIdentity;

public class IslandId extends BasicIdentity<Integer> {

	public IslandId() {
	}

	public IslandId(Integer id) {
		super(id);
	}

	public static IslandId of(Integer id) {
		return new IslandId(id);
	}

}
