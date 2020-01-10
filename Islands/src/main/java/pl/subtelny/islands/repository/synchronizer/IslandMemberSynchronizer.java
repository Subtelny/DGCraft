package pl.subtelny.islands.repository.synchronizer;

import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.IslandMember;
import pl.subtelny.islands.model.IslandMemberType;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.repository.storage.SkyblockIslandStorage;

@Component
public class IslandMemberSynchronizer {

    private final SkyblockIslandStorage skyblockIslandStorage;

    private final IslanderSynchronizer islanderSynchronizer;

    @Autowired
    public IslandMemberSynchronizer(SkyblockIslandStorage skyblockIslandStorage) {
        this.skyblockIslandStorage = skyblockIslandStorage;
        this.islanderSynchronizer = buildIslanderSynchronizer();
    }

    public void synchronizeIslandMember(IslandMember islandMember) {
        if (islandMember.getIslandMemberType() == IslandMemberType.ISLANDER) {
            islanderSynchronizer.synchronizeIslander((Islander) islandMember);
        }
    }

    private IslanderSynchronizer buildIslanderSynchronizer() {
        return new IslanderSynchronizer(
                skyblockIslandStorage);
    }

}
