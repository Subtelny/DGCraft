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
import pl.subtelny.utilities.Callback;

import java.util.UUID;

public abstract class WorldEditOperationSession implements OperationSession {

    private final static IAsyncWorldEdit aweAPI = (IAsyncWorldEdit) Bukkit.getServer().getPluginManager().getPlugin("AsyncWorldEdit");

    private IPlayerEntry player;

    private Callback<OperationStatus> state;

    protected void runOperation(WorldEditAction worldEditAction) {
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
    public void setStateListener(Callback<OperationStatus> state) {
        this.state = state;
    }

    private static final class OperationJob {

        private static final IBlockPlacer BLOCK_PLACER = aweAPI.getBlockPlacer();

        private final IThreadSafeEditSession session;

        private final IPlayerEntry fakePlayer;

        private final WorldEditAction worldEditAction;

        private final Callback<OperationStatus> state;

        private OperationJob(IThreadSafeEditSession session, IPlayerEntry fakePlayer, WorldEditAction worldEditAction, Callback<OperationStatus> state) {
            this.session = session;
            this.fakePlayer = fakePlayer;
            this.worldEditAction = worldEditAction;
            this.state = state;
        }

        public void execute() {
            String jobName = fakePlayer.getName();
            BLOCK_PLACER.performAsAsyncJob(session, fakePlayer, jobName, worldEditAction);
            addStateListener();
        }

        private void addStateListener() {
            if (state == null) {
                return;
            }
            IJobEntry job = getJob();
            job.addStateChangedListener(iJobEntry -> {
                OperationStatus operationStatus = OperationStatus.fromJobStatus(iJobEntry.getStatus());
                state.done(operationStatus);
            });
        }

        private IJobEntry getJob() {
            return OperationJob.BLOCK_PLACER.getJob(fakePlayer, 0);
        }

    }
}
