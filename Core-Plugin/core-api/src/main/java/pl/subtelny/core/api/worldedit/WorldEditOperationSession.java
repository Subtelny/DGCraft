package pl.subtelny.core.api.worldedit;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.primesoft.asyncworldedit.api.IAsyncWorldEdit;
import org.primesoft.asyncworldedit.api.blockPlacer.IBlockPlacer;
import org.primesoft.asyncworldedit.api.blockPlacer.entries.IJobEntry;
import org.primesoft.asyncworldedit.api.playerManager.IPlayerEntry;
import org.primesoft.asyncworldedit.api.playerManager.IPlayerManager;
import org.primesoft.asyncworldedit.api.worldedit.IAsyncEditSessionFactory;
import org.primesoft.asyncworldedit.api.worldedit.IThreadSafeEditSession;
import pl.subtelny.utilities.job.JobRun;
import pl.subtelny.utilities.job.JobsProvider;
import pl.subtelny.utilities.Callback;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class WorldEditOperationSession implements OperationSession {

    private final static IAsyncWorldEdit aweAPI = (IAsyncWorldEdit) Bukkit.getServer().getPluginManager().getPlugin("AsyncWorldEdit");

    private IPlayerEntry player;

    private Callback<Integer> state;

    protected void runOperation(WorldEditAction worldEditAction) throws InterruptedException {
        OperationJob operationJob = new OperationJob(createSession(), getFakePlayer(), worldEditAction, state);
        operationJob.execute();
    }

    private IPlayerEntry getFakePlayer() {
        if (player != null) {
            return player;
        }
        IPlayerManager playerManager = aweAPI.getPlayerManager();
        player = playerManager.createFakePlayer("Test", UUID.randomUUID());
        return player;
    }

    private IThreadSafeEditSession createSession() {
        IAsyncEditSessionFactory editSessionFactory = getAsyncEditSessionFactory();
        BukkitWorld bukkitWorld = new BukkitWorld(getWorld());
        IPlayerEntry fakePlayer = getFakePlayer();
        return editSessionFactory.getThreadSafeEditSession(bukkitWorld, -1, null, fakePlayer);
    }

    private static IAsyncEditSessionFactory getAsyncEditSessionFactory() {
        return (IAsyncEditSessionFactory) WorldEdit.getInstance().getEditSessionFactory();
    }

    protected abstract World getWorld();

    @Override
    public void cancel() {
        IBlockPlacer blockPlacer = aweAPI.getBlockPlacer();
        IJobEntry job = blockPlacer.getJob(getFakePlayer(), 0);
        job.cancel();
    }

    @Override
    public void setStateListener(Callback<Integer> state) {
        this.state = state;
    }

    private static final class OperationJob {

        private static final IBlockPlacer BLOCK_PLACER = aweAPI.getBlockPlacer();

        private final IThreadSafeEditSession session;

        private final IPlayerEntry fakePlayer;

        private final WorldEditAction worldEditAction;

        private final Callback<Integer> state;

        private OperationJob(IThreadSafeEditSession session, IPlayerEntry fakePlayer, WorldEditAction worldEditAction, Callback<Integer> state) {
            this.session = session;
            this.fakePlayer = fakePlayer;
            this.worldEditAction = worldEditAction;
            this.state = state;
        }

        public void execute() throws InterruptedException {
            String jobName = fakePlayer.getName();
            BLOCK_PLACER.performAsAsyncJob(session, fakePlayer, jobName, worldEditAction);
            addStateListener();
            waitTillEnd();
        }

        private void addStateListener() {
            if (state == null) {
                return;
            }
            IJobEntry job = getJob();
            job.addStateChangedListener(iJobEntry -> state.done(iJobEntry.getStatus().getSeqNumber()));
        }

        private void waitTillEnd() throws InterruptedException {
            IJobEntry job = getJob();
            if (job != null && !job.isTaskDone()) {
                CountDownLatch latch = new CountDownLatch(1);
                job.addStateChangedListener(iJobEntry -> {
                    if (iJobEntry.isTaskDone()) {
                        latch.countDown();
                    }
                });
                latch.await(1, TimeUnit.MINUTES);
            }
        }

        private IJobEntry getJob() {
            return OperationJob.BLOCK_PLACER.getJob(fakePlayer, 0);
        }

    }
}
