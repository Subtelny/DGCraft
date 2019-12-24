package pl.subtelny.entity;

import pl.subtelny.identity.BasicIdentity;

public class Entity<T extends BasicIdentity> {

    private final T id;

    protected boolean anyChanges;

    public Entity(T id) {
        this.id = id;
    }

    public boolean isAnyChanges() {
        return anyChanges;
    }

    protected void setAnyChanges(boolean anyChanges) {
        this.anyChanges = anyChanges;
    }

    public T getId() {
        return id;
    }

}
