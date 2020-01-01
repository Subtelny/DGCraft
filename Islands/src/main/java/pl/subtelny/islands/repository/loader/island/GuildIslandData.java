package pl.subtelny.islands.repository.loader.island;

import java.time.LocalDateTime;

public class GuildIslandData extends IslandData {

	private int owner;

	private LocalDateTime protection;

	public GuildIslandData(int owner, LocalDateTime protection, IslandData islandData) {
		super(islandData.getIslandId(), islandData.getIslandType(), islandData.getCreatedDate(), islandData.getSpawn());
		this.owner = owner;
		this.protection = protection;
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
