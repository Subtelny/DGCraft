package pl.subtelny.islands.island;

import pl.subtelny.groups.api.*;

import java.util.List;
import java.util.Optional;

public class IslandGroupsContext implements GroupsContext {

    public static final GroupId OWNER = GroupId.of("OWNER");

    private final GroupsContext groupsContext;

    public IslandGroupsContext(GroupsContext groupsContext) {
        this.groupsContext = groupsContext;
    }

    @Override
    public GroupsContextId getId() {
        return null;
    }

    @Override
    public Optional<Group> getGroup(GroupId groupId) {
        return groupsContext.getGroup(groupId);
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
