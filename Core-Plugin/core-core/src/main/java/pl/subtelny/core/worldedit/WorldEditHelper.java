package pl.subtelny.core.worldedit;

import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.primesoft.asyncworldedit.api.IAsyncWorldEdit;
import org.primesoft.asyncworldedit.api.blockPlacer.IBlockPlacer;
import org.primesoft.asyncworldedit.api.blockPlacer.IBlockPlacerListener;
import org.primesoft.asyncworldedit.api.blockPlacer.entries.IJobEntry;
import org.primesoft.asyncworldedit.api.playerManager.IPlayerEntry;
import org.primesoft.asyncworldedit.api.playerManager.IPlayerManager;
import org.primesoft.asyncworldedit.api.utils.IFuncParamEx;
import org.primesoft.asyncworldedit.api.worldedit.IAsyncEditSessionFactory;
import org.primesoft.asyncworldedit.api.worldedit.ICancelabeEditSession;
import org.primesoft.asyncworldedit.api.worldedit.IThreadSafeEditSession;

public final class WorldEditHelper {

	private final static IAsyncWorldEdit aweAPI = (IAsyncWorldEdit) Bukkit.getServer().getPluginManager().getPlugin("AsyncWorldEdit");

	private static IAsyncEditSessionFactory getAsyncEditSessionFactory() {
		return (IAsyncEditSessionFactory) WorldEdit.getInstance().getEditSessionFactory();
	}

	public static void pasteSchematic(Location location, File schematic, IBlockPlacerListener iBlockPlacerListener) throws IOException {
		IPlayerManager playerManager = aweAPI.getPlayerManager();
		IPlayerEntry fakePlayer = playerManager.createFakePlayer("WEH-LS", UUID.randomUUID());

		IAsyncEditSessionFactory editSessionFactory = getAsyncEditSessionFactory();
		World world = new BukkitWorld(location.getWorld());
		IThreadSafeEditSession safeEditSession = editSessionFactory.getThreadSafeEditSession(world, -1, null, fakePlayer);

		ClipboardReader reader = BuiltInClipboardFormat.MCEDIT_SCHEMATIC.getReader(new FileInputStream(schematic));
		Clipboard clipboard = reader.read();
		ClipboardHolder holder = new ClipboardHolder(clipboard);

		PasteAction pasteAction = new PasteAction(holder, location);

		IBlockPlacer blockPlacer = aweAPI.getBlockPlacer();
		blockPlacer.addListener(iBlockPlacerListener);
		blockPlacer.performAsAsyncJob(safeEditSession, fakePlayer, "pasteSchematic-" + schematic.getName() + "-at-" + location.toString(), pasteAction);
	}

	private static class PasteAction implements IFuncParamEx<Integer, ICancelabeEditSession, MaxChangedBlocksException> {

		private final ClipboardHolder holder;

		private final Location location;

		private PasteAction(ClipboardHolder clipboardHolder, Location location) {
			this.holder = clipboardHolder;
			this.location = location;
		}

		@Override
		public Integer execute(ICancelabeEditSession editSession) throws MaxChangedBlocksException {
			editSession.enableQueue();
			editSession.setFastMode(true);

			BlockVector3 to = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
			final Operation build = holder.createPaste(editSession)
					.to(to)
					.ignoreAirBlocks(true)
					.build();

			Operations.completeBlindly(build);
			editSession.flushSession();
			return 1;
		}

	}

}
