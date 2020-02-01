package pl.subtelny.islands.settings;

import org.bukkit.World;

import java.io.File;

public class Settings {

	public static class GuildIsland {

		public static World ISLAND_WORLD;

	}

	public static class SkyblockIsland {

		public static World ISLAND_WORLD;

		public static int SPACE_BETWEEN_ISLANDS;

		public static int ISLAND_SIZE;

		public static int EXTEND_LEVELS;

		public static int EXTEND_SIZE_PER_LEVEL;

		public static void loadFromFile(File file) {
			//kod
		}

	}

}
