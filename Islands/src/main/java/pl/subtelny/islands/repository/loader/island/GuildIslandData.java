package pl.subtelny.islands.repository.loader.island;

import java.time.LocalDateTime;
import pl.subtelny.utils.cuboid.Cuboid;

public class GuildIslandData extends IslandData {

	private int owner;

	private LocalDateTime protection;

	private Cuboid cuboid;

	public GuildIslandData(int owner, LocalDateTime protection, IslandData islandData, Cuboid cuboid) {
		super(islandData.getIslandId(), islandData.getIslandType(), islandData.getCreatedDate(), islandData.getSpawn());
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

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public LocalDateTime getProtection() {
		return protection;
	}

	public void setProtection(LocalDateTime protection) {
		this.protection = protection;
	}
}
