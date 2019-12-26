package pl.subtelny.islands.service;

import org.bukkit.entity.Player;

public class SkyblockIslandCreateRequest {

	private final Player owner;

	public SkyblockIslandCreateRequest(Player owner) {
		this.owner = owner;
	}
}
