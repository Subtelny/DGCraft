package pl.subtelny.groups.api;

import pl.subtelny.utilities.identity.BasicIdentity;

public class GroupsContextId extends BasicIdentity<String> {

    public GroupsContextId(String id) {
        super(id);
    }

    public static GroupsContextId of(String id) {
        return new GroupsContextId(id);
    }

}
