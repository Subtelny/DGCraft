package pl.subtelny.islands.api.module.component;

import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandId;
import pl.subtelny.islands.api.IslandMember;

public interface DeleteComponent<MEMBER extends IslandMember, ISLAND extends Island> extends IslandComponent {

    void delete(MEMBER islandMember, IslandId islandId);

}
