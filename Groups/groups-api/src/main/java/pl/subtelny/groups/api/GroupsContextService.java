package pl.subtelny.groups.api;

public interface GroupsContextService {

    GroupsContext getOrCreateGroupsContext(GroupsContextId groupsContextId);

}
