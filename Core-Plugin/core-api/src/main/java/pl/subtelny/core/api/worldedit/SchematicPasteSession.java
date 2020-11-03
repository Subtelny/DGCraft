package pl.subtelny.core.api.worldedit;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class SchematicPasteSession extends WorldEditOperationSession {

    private final File schematic;

    private final Location location;

    public SchematicPasteSession(File schematic, Location location) {
        this.schematic = schematic;
        this.location = location;
    }

    @Override
    public CompletableFuture<Void> performAsync() {
        try {
            ClipboardReader reader = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getReader(new FileInputStream(schematic));
            Clipboard clipboard = reader.read();
            ClipboardHolder holder = new ClipboardHolder(clipboard);

            PasteAction pasteAction = new PasteAction(holder, location);
            return runOperationAsync(pasteAction);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new IllegalStateException("There is problem with paste schematic: " + e.getMessage()));
        }
    }

    @Override
    protected World getWorld() {
        return location.getWorld();
    }
}
