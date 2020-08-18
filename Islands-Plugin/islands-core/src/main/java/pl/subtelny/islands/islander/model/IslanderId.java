package pl.subtelny.islands.islander.model;

import pl.subtelny.utilities.identity.BasicIdentity;

import java.util.UUID;

public class IslanderId extends BasicIdentity<UUID> {

    public IslanderId(UUID id) {
        super(id);
    }

    public static IslanderId of(UUID id) {
        return new IslanderId(id);
    }
}