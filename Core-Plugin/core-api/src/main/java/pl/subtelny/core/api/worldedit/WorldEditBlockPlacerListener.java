package pl.subtelny.core.api.worldedit;

import org.primesoft.asyncworldedit.api.blockPlacer.IBlockPlacer;
import org.primesoft.asyncworldedit.api.blockPlacer.IBlockPlacerListener;
import org.primesoft.asyncworldedit.api.blockPlacer.entries.IJobEntry;

import java.util.Objects;
import java.util.function.Consumer;

public class WorldEditBlockPlacerListener implements IBlockPlacerListener {

    private final String jobName;

    private final Consumer<Boolean> consumer;

    private final IBlockPlacer iBlockPlacer;

    public WorldEditBlockPlacerListener(String jobName, Consumer<Boolean> consumer, IBlockPlacer iBlockPlacer) {
        this.jobName = jobName;
        this.consumer = consumer;
        this.iBlockPlacer = iBlockPlacer;
    }

    @Override
    public void jobAdded(IJobEntry iJobEntry) {

    }

    @Override
    public void jobRemoved(IJobEntry iJobEntry) {
        if (iJobEntry.getName().equals(jobName)) {
            consumer.accept(iJobEntry.isTaskDone());
            iBlockPlacer.removeListener(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldEditBlockPlacerListener that = (WorldEditBlockPlacerListener) o;
        return Objects.equals(jobName, that.jobName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobName);
    }
}
