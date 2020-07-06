package pl.subtelny.islands.settings;

import org.bukkit.World;
import pl.subtelny.islands.skyblockisland.extendcuboid.settings.SkyblockIslandExtendOptions;

import java.io.File;

public class Settings {

	public static class GuildIsland {

		public static World ISLAND_WORLD;

	}

	public static class SkyblockIsland {

		public static SkyblockIslandExtendOptions EXTEND_OPTIONS;

		public static World ISLAND_WORLD;

		public static int SPACE_BETWEEN_ISLANDS;

		public static int ISLAND_SIZE;

		private static final int MINUS_EXTEND_LEVEL_ZERO = 1;

		public static void loadFromFile(File file) {
			//kod
		}

		public static int getTotalExtendLevels() {
			return EXTEND_OPTIONS.getIslandExtends().size() - MINUS_EXTEND_LEVEL_ZERO;
		}

		public static int getIslandSizeToExtendLevel(int extendLevel) {
			return (ISLAND_SIZE - EXTEND_OPTIONS.getTotalExtendSize()) +
					EXTEND_OPTIONS.getSumOfExtendSizeToLevel(extendLevel);
		}



	}

}
