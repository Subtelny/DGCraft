package pl.subtelny.islands.skyblockisland.repository.updater;

import org.jooq.Configuration;
import pl.subtelny.islands.skyblockisland.model.SkyblockIsland;
import pl.subtelny.islands.skyblockisland.repository.anemia.SkyblockIslandAnemia;
import pl.subtelny.repository.Updater;

import java.util.concurrent.CompletableFuture;

public class SkyblockIslandUpdater extends Updater<SkyblockIsland> {

    private final Configuration configuration;

    public SkyblockIslandUpdater(Configuration configuration) {
        this.configuration = configuration;
    }

    public void update(SkyblockIsland skyblockIsland) {
        performAction(skyblockIsland);
    }

    public CompletableFuture<Integer> updateAsync(SkyblockIsland skyblockIsland) {
        return performActionAsync(skyblockIsland);
    }

    @Override
    protected void performAction(SkyblockIsland skyblockIsland) {
        SkyblockIslandAnemiaUpdateAction action = new SkyblockIslandAnemiaUpdateAction(configuration);
        SkyblockIslandAnemia anemia = SkyblockIslandAnemiaFactory.toAnemia(skyblockIsland);
        action.perform(anemia);
    }

    @Override
    protected CompletableFuture<Integer> performActionAsync(SkyblockIsland skyblockIsland) {
        SkyblockIslandAnemiaUpdateAction action = new SkyblockIslandAnemiaUpdateAction(configuration);
        SkyblockIslandAnemia anemia = SkyblockIslandAnemiaFactory.toAnemia(skyblockIsland);
        return action.performAsync(anemia).toCompletableFuture();
    }

}
