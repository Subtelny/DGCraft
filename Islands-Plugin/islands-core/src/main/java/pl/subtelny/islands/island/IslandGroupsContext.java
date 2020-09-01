package pl.subtelny.islands.island;

import pl.subtelny.groups.api.Group;
import pl.subtelny.groups.api.GroupMemberId;
import pl.subtelny.groups.api.GroupsContext;

import java.util.List;

public class IslandGroupsContext implements GroupsContext {

    private final GroupsContext groupsContext;

    public IslandGroupsContext(GroupsContext groupsContext) {
        this.groupsContext = groupsContext;
    }

    @Override
    public List<Group> getGroups() {
        return groupsContext.getGroups();
    }

    @Override
    public List<Group> getGroups(GroupMemberId groupMemberId) {
        return groupsContext.getGroups(groupMemberId);
    }

    @Override
    public boolean hasPermission(GroupMemberId groupMemberId, String permission) {
        return groupsContext.hasPermission(groupMemberId, permission);
    }

}
