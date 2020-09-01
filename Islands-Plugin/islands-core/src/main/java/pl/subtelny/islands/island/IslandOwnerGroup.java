package pl.subtelny.islands.island;

import pl.subtelny.groups.api.Group;
import pl.subtelny.groups.api.GroupId;
import pl.subtelny.groups.api.GroupMemberId;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class IslandOwnerGroup implements Group {

    public static final GroupId ID = GroupId.of("OWNER");

    private final List<GroupMemberId> groupMemberIds = new ArrayList<>();

    @Override
    public List<GroupMemberId> getGroupMembers() {
        return new ArrayList<>(groupMemberIds);
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public void addGroupMember(GroupMemberId groupMemberId) {
        if (groupMemberIds.size() > 0) {
            throw ValidationException.of("group.islandowner.out_of_size");
        }
        groupMemberIds.add(groupMemberId);
    }

    @Override
    public void removeGroupMember(GroupMemberId groupMemberId) {
        groupMemberIds.remove(groupMemberId);
    }

}
