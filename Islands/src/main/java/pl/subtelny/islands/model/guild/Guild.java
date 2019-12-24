package pl.subtelny.islands.model.guild;

import java.util.Optional;
import javax.annotation.Nullable;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandMember;

public class Guild implements IslandMember {

	private Island island;

	public Optional<Island> getIsland() {
		return Optional.ofNullable(island);
	}

	public void setIsland(@Nullable Island island) {

	}

}
