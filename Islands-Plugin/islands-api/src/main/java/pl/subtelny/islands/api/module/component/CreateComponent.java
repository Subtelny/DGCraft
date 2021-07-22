package pl.subtelny.islands.api.module.component;

import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandMember;

import java.util.concurrent.CompletableFuture;

public interface CreateComponent<MEMBER extends IslandMember, ISLAND extends Island> extends IslandComponent {

    CompletableFuture<ISLAND> create(MEMBER islandMember);

}
