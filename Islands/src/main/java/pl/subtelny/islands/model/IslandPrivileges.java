package pl.subtelny.islands.model;

import com.google.common.collect.Lists;

import java.util.List;

public class IslandPrivileges {

    private List<IslandPrivilege> privileges = Lists.newArrayList();

    public boolean isOwner() {
        return hasPrivilage(IslandPrivilege.OWNER);
    }

    public boolean isCoOwner() {
        return hasPrivilage(IslandPrivilege.CO_OWNER);
    }

    public void addPrivilage(IslandPrivilege islandPrivilage) {
        privileges.add(islandPrivilage);
    }
    
    public void removePrivilage(IslandPrivilege islandPrivilage) {
        privileges.remove(islandPrivilage);
    }
    
    public boolean hasPrivilage(IslandPrivilege islandPrivilage) {
        return privileges.contains(islandPrivilage);
    }

    public List<IslandPrivilege> getPrivileges() {
        return Lists.newArrayList(privileges);
    }

}
