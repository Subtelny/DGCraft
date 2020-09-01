package pl.subtelny.groups.api;

import java.util.List;

public interface GroupsContext {

    List<Group> getGroups();

    List<Group> getGroups(GroupMemberId groupMemberId);

    boolean hasPermission(GroupMemberId groupMemberId, String permission);

}
