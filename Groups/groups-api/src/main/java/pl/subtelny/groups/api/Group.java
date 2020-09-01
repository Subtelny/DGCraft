package pl.subtelny.groups.api;

import java.util.List;

public interface Group {

    List<GroupMemberId> getGroupMembers();

    boolean hasPermission(String permission);

    void addGroupMember(GroupMemberId groupMemberId);

    void removeGroupMember(GroupMemberId groupMemberId);

}
