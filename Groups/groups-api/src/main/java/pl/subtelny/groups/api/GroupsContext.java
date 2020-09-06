package pl.subtelny.groups.api;

import java.util.List;
import java.util.Optional;

public interface GroupsContext {

    Optional<Group> getGroup(GroupId groupId);

    List<Group> getGroups();

    List<Group> getGroups(GroupMemberId groupMemberId);

    boolean hasPermission(GroupMemberId groupMemberId, String permission);

}
