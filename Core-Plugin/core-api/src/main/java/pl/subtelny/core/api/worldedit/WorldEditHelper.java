package pl.subtelny.core.api.worldedit;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.primesoft.asyncworldedit.api.IAsyncWorldEdit;
import org.primesoft.asyncworldedit.api.blockPlacer.IBlockPlacer;
import org.primesoft.asyncworldedit.api.playerManager.IPlayerEntry;
import org.primesoft.asyncworldedit.api.playerManager.IPlayerManager;
import org.primesoft.asyncworldedit.api.worldedit.IAsyncEditSessionFactory;
import org.primesoft.asyncworldedit.api.worldedit.IThreadSafeEditSession;
import pl.subtelny.utilities.Validation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.function.Consumer;

public final class WorldEditHelper {

    private final static IAsyncWorldEdit aweAPI = (IAsyncWorldEdit) Bukkit.getServer().getPluginManager().getPlugin("AsyncWorldEdit");

    public static void clearRegion(Location pos1, Location pos2, Consumer<Boolean> consumer) {
        Validation.isTrue(aweAPI != null, "AsyncWorldEdit not found");
        IPlayerEntry fakePlayer = buildPlayer("WEH-CR");

        World world = new BukkitWorld(pos1.getWorld());
        String jobName = "clearRegion-" + pos1.toString() + "," + pos2.toString();
        ClearAction clearAction = new ClearAction(pos1, pos2);
        performAction(consumer, world, fakePlayer, clearAction, jobName);
    }

    public static void pasteSchematic(Location location, File schematic, Consumer<Boolean> consumer) throws IOException {
        Validation.isTrue(aweAPI != null, "AsyncWorldEdit not found");
        IPlayerEntry fakePlayer = buildPlayer("WEH-LS");

        ClipboardReader reader = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getReader(new FileInputStream(schematic));
        Clipboard clipboard = reader.read();
        ClipboardHolder holder = new ClipboardHolder(clipboard);

        World world = new BukkitWorld(location.getWorld());
        String jobName = "pasteSchematic-" + schematic.getName() + "-at-" + location.toString();
        PasteAction pasteAction = new PasteAction(holder, location);
        performAction(consumer, world, fakePlayer, pasteAction, jobName);
    }

    private static IPlayerEntry buildPlayer(String playerName) {
        IPlayerManager playerManager = aweAPI.getPlayerManager();
        return playerManager.createFakePlayer(playerName, UUID.randomUUID());
    }

    private static void performAction(Consumer<Boolean> consumer, World world, IPlayerEntry player, WorldEditAction action, String jobName) {
        IAsyncEditSessionFactory editSessionFactory = getAsyncEditSessionFactory();
        IThreadSafeEditSession safeEditSession = editSessionFactory.getThreadSafeEditSession(world, -1, null, player);
        IBlockPlacer blockPlacer = aweAPI.getBlockPlacer();
        blockPlacer.addListener(new WorldEditBlockPlacerListener(jobName, consumer, blockPlacer));
        blockPlacer.performAsAsyncJob(safeEditSession, player, jobName, action);
    }

    private static IAsyncEditSessionFactory getAsyncEditSessionFactory() {
        return (IAsyncEditSessionFactory) WorldEdit.getInstance().getEditSessionFactory();
    }

}
