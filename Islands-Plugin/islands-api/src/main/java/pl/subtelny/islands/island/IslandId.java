package pl.subtelny.islands.island;

import pl.subtelny.utilities.identity.BasicIdentity;

public class IslandId extends BasicIdentity<Integer> {

	public IslandId(Integer id) {
		super(id);
	}

	public static IslandId of(Integer id) {
		return new IslandId(id);
	}
}
