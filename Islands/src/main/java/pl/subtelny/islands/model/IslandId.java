package pl.subtelny.islands.model;

import pl.subtelny.identity.BasicIdentity;

public class IslandId extends BasicIdentity<Long> {

	public IslandId() {
	}

	public IslandId(Long id) {
		super(id);
	}

	public static IslandId of(Long id) {
		return new IslandId(id);
	}

}
