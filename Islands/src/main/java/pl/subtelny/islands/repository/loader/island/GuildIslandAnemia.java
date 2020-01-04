package pl.subtelny.islands.repository.loader.island;

import java.time.LocalDateTime;
import pl.subtelny.islands.model.guild.GuildId;
import pl.subtelny.utils.cuboid.Cuboid;

public class GuildIslandAnemia extends IslandAnemia {

	private GuildId owner;

	private LocalDateTime protection;

	private Cuboid cuboid;

	public GuildIslandAnemia(IslandAnemia islandAnemia, GuildId owner, LocalDateTime protection, Cuboid cuboid) {
		super(islandAnemia.getIslandId(), islandAnemia.getIslandType(), islandAnemia.getCreatedDate(), islandAnemia.getSpawn());
		this.owner = owner;
		this.protection = protection;
		this.cuboid = cuboid;
	}

	public Cuboid getCuboid() {
		return cuboid;
	}

	public void setCuboid(Cuboid cuboid) {
		this.cuboid = cuboid;
	}

	public GuildId getOwner() {
		return owner;
	}

	public void setOwner(GuildId owner) {
		this.owner = owner;
	}

	public LocalDateTime getProtection() {
		return protection;
	}

	public void setProtection(LocalDateTime protection) {
		this.protection = protection;
	}
}
