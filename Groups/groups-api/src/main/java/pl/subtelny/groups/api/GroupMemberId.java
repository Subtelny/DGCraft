package pl.subtelny.groups.api;

import pl.subtelny.utilities.identity.BasicIdentity;

public class GroupMemberId extends BasicIdentity<String> {

    public GroupMemberId(String id) {
        super(id);
    }

    public static GroupMemberId of(String id) {
        return new GroupMemberId(id);
    }

}
