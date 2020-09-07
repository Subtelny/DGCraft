package pl.subtelny.groups.api;

public interface GroupsContextService {

    GroupsContext getOrCreateGroupsContext(GroupsContextId groupsContextId);

    void createGroup(GroupsContextId groupsContextId, GroupId groupId);

    void addMember(GroupsContextId groupsContextId, GroupId groupId, GroupMemberId groupMemberId);

}
