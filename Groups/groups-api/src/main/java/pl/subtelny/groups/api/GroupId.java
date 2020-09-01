package pl.subtelny.groups.api;

import pl.subtelny.utilities.identity.BasicIdentity;

public class GroupId extends BasicIdentity<String> {

    public GroupId(String id) {
        super(id);
    }

    public static GroupId of(String value) {
        return new GroupId(value);
    }

}
