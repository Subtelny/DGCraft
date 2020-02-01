package pl.subtelny.islands.repository.island.anemia;

import java.time.LocalDateTime;
import org.bukkit.Location;
import pl.subtelny.islands.model.island.IslandType;
import pl.subtelny.islands.model.guild.GuildId;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.utils.cuboid.Cuboid;

public class GuildIslandAnemia extends IslandAnemia {

	private GuildId owner;

	private LocalDateTime protection;

	private Cuboid cuboid;

	public GuildIslandAnemia() {
	}

	public GuildIslandAnemia(IslandId islandId, LocalDateTime createdDate, Location spawn, GuildId owner, LocalDateTime protection, Cuboid cuboid) {
		super(islandId, createdDate, spawn);
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

	@Override
	public IslandType getIslandType() {
		return IslandType.GUILD;
	}
}
