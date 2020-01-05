package pl.subtelny.islands.repository.synchronizer;

import org.jooq.Configuration;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.islands.model.IslandMember;
import pl.subtelny.islands.model.IslandMemberType;

@Component
public class IslandMemberSynchronizer {

    private final Configuration configuration;

    @Autowired
    public IslandMemberSynchronizer(Configuration configuration) {
        this.configuration = configuration;
    }

    public void synchronizeIslandMember(IslandMember islandMember) {
        if (islandMember.getIslandMemberType() == IslandMemberType.ISLANDER) {

        }

    }

}
