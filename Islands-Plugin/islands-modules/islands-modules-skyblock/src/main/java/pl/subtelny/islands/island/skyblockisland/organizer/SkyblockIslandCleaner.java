package pl.subtelny.islands.island.skyblockisland.organizer;

import org.bukkit.Location;
import pl.subtelny.core.api.worldedit.ClearRegionSession;
import pl.subtelny.core.api.worldedit.OperationStatus;
import pl.subtelny.utilities.Callback;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SkyblockIslandCleaner {

    public void cleanBlocksAt(Cuboid cuboid) {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            cleanBlocksAt(cuboid.getLowerNE(), cuboid.getUpperSW(), latch);
            boolean await = latch.await(5, TimeUnit.MINUTES);
            if (!await) {
                throw new IllegalStateException("Could not end clean blocks within 5 minutes at " + cuboid.toString());
            }
        } catch (IllegalStateException | InterruptedException e) {
            throw new IllegalStateException("Error during cleaning blocks at " + cuboid.toString(), e);
        }
    }

    private void cleanBlocksAt(Location loc1, Location loc2, CountDownLatch latch) {
        ClearRegionSession session = new ClearRegionSession(loc1, loc2);
        session.setStateListener(getOperationStatusCallback(latch));
        session.perform();
    }

    private Callback<OperationStatus> getOperationStatusCallback(CountDownLatch latch) {
        return operationStatus -> {
            if (operationStatus.isEnded()) {
                latch.countDown();
            }
        };
    }

}
