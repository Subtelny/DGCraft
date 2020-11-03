package pl.subtelny.islands.region;

import pl.subtelny.core.api.worldedit.WorldEditHelper;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.exception.ValidationException;
import pl.subtelny.utilities.job.Job;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class RegionCleaner implements Job<Void> {

    private final Cuboid cuboid;

    public RegionCleaner(Cuboid cuboid) {
        this.cuboid = cuboid;
    }

    @Override
    public Void execute() {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            WorldEditHelper.clearRegion(cuboid.getLowerNE(), cuboid.getUpperSW(), aBoolean -> latch.countDown());
            latch.await(1, TimeUnit.MINUTES);
        } catch (InterruptedException | IllegalStateException e) {
            throw ValidationException.of("region_cleaner.error_while_cleaning_region", e.getMessage());
        }
        return null;
    }

}
