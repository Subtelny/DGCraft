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

public class SchematicPasteSession extends WorldEditOperationSession {

    private final File schematic;

    private final Location location;

    public SchematicPasteSession(File schematic, Location location) {
        this.schematic = schematic;
        this.location = location;
    }

    @Override
    public void perform() throws IOException, InterruptedException {
        ClipboardReader reader = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getReader(new FileInputStream(schematic));
        Clipboard clipboard = reader.read();
        ClipboardHolder holder = new ClipboardHolder(clipboard);

        PasteAction pasteAction = new PasteAction(holder, location);
        runOperation(pasteAction);
    }

    @Override
    protected World getWorld() {
        return location.getWorld();
    }
}
