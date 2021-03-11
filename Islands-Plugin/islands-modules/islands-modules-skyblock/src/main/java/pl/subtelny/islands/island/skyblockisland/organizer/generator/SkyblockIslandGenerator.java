package pl.subtelny.islands.island.skyblockisland.organizer.generator;

import org.bukkit.Location;
import pl.subtelny.core.api.worldedit.OperationStatus;
import pl.subtelny.core.api.worldedit.SchematicPasteSession;
import pl.subtelny.utilities.Callback;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.exception.ValidationException;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SkyblockIslandGenerator {

    public void generateIsland(Location location, File schematicFile, Callback<OperationStatus> callback) {
        try {
            pasteSchematicWaitForComplete(schematicFile, location, callback);
        } catch (IllegalStateException | InterruptedException e) {
            throw new ValidationException("skyblockIslandGenerator.exception_while_pasting_schematic", e);
        }
    }

    private void pasteSchematicWaitForComplete(File schematicFile, Location location, Callback<OperationStatus> callback) throws InterruptedException {
        Validation.isTrue(schematicFile.exists(), "skyblockIslandCreator.not_found_schematic_file");
        SchematicPasteSession pasteSession = new SchematicPasteSession(schematicFile, location);
        CountDownLatch latch = new CountDownLatch(1);
        pasteSession.setStateListener(getStateListener(callback, latch));
        pasteSession.perform();
        boolean await = latch.await(20, TimeUnit.SECONDS);
        if (!await) {
            throw new ValidationException("skyblockIslandGenerator.paste_not_completed");
        }
    }

    private Callback<OperationStatus> getStateListener(Callback<OperationStatus> callback, CountDownLatch latch) {
        return operationStatus -> {
            callback.done(operationStatus);
            if (operationStatus.isEnded()) {
                latch.countDown();
            }
        };
    }
}
