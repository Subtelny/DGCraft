package pl.subtelny.core.api.worldedit;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SchematicPasteSession extends WorldEditOperationSession {

    private final File schematic;

    private final Location location;

    public SchematicPasteSession(File schematic, Location location) {
        this.schematic = schematic;
        this.location = location;
    }

    @Override
    public void perform() {
        ClipboardReader reader = getReader();
        Clipboard clipboard = getRead(reader);
        ClipboardHolder holder = new ClipboardHolder(clipboard);

        PasteAction pasteAction = new PasteAction(holder, location);
        runOperation(pasteAction);
    }

    private ClipboardReader getReader() {
        try {
            return BuiltInClipboardFormat.SPONGE_SCHEMATIC.getReader(getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Error while reading from schematic", e);
        }
    }

    private FileInputStream getInputStream() {
        try {
            return new FileInputStream(schematic);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Not found schematic file", e);
        }
    }

    private Clipboard getRead(ClipboardReader reader) {
        try {
            return reader.read();
        } catch (IOException e) {
            throw new IllegalStateException("Error while reading schematic clipboard", e);
        }
    }

    @Override
    protected World getWorld() {
        return location.getWorld();
    }
}
