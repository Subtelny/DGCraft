package pl.subtelny.islands.model.guild;

import pl.subtelny.utilities.BasicIdentity;

public class GuildId extends BasicIdentity<Integer> {

	public GuildId() {
	}

	public GuildId(int identity) {
		super(identity);
	}

	public static GuildId of(int identity) {
		return new GuildId(identity);
	}

}
