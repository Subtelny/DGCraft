package pl.subtelny.islands.service;

import org.bukkit.entity.Player;

public class SkyblockIslandCreateRequest {

	private final Player owner;

	private final String schematic;

	public SkyblockIslandCreateRequest(Player owner, String schematic) {
		this.owner = owner;
		this.schematic = schematic;
	}

	public Player getOwner() {
		return owner;
	}

	public String getSchematic() {
		return schematic;
	}
}
