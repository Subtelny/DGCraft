package pl.subtelny.islands.api.module.component;

import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandMember;

public interface InviteComponent<ASKER extends IslandMember, TARGET extends IslandMember, ISLAND extends Island> extends IslandComponent {

    void invite(ASKER inviter, TARGET target);

    void ask(ASKER asker, TARGET target);

    void ask(ASKER asker, ISLAND target);

}
