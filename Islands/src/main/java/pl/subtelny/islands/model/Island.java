package pl.subtelny.islands.model;

import com.google.common.collect.Lists;
import org.apache.commons.lang.Validate;
import pl.subtelny.islands.IslandFlag;
import pl.subtelny.utils.cuboid.Cuboid;
import pl.subtelny.validation.ValidationException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Island {

    private final IslandId islandId;

    private final Cuboid cuboid;

    private List<Member> members = Lists.newArrayList();

    private List<IslandFlag> flags = Lists.newArrayList();

    public Island(IslandId islandId, Cuboid cuboid) {
        this.islandId = islandId;
        this.cuboid = cuboid;
    }

    public void changeOwner(Member newOwner) {
        validateIsMemberOfIsland(newOwner);
        Optional<Member> currentOwnerOpt = getOwner();
        if (currentOwnerOpt.isPresent()) {
            Member currentOwner = currentOwnerOpt.get();
            validateSameOwner(newOwner.getUuid(), currentOwner.getUuid());
            currentOwner.getIslandPrivileges().removePrivilage(IslandPrivilege.OWNER);
        }
        newOwner.getIslandPrivileges().addPrivilage(IslandPrivilege.OWNER);
    }

    private void validateIsMemberOfIsland(Member newOwner) {
        if (!getMembers().contains(newOwner)) {
            throw new ValidationException("This member is not added into island");
        }
    }

    private void validateSameOwner(UUID newOwner, UUID currentOwner) {
        if (currentOwner.equals(newOwner)) {
            throw new ValidationException("Owner with same uuid is already set");
        }
    }

    public void addMember(Member member) {
        validateAddMember(member.getUuid(), member.getIslandPrivileges());
        validatePrivileges(member.getIslandPrivileges());
        members.add(member);
    }

    private void validateAddMember(UUID uuid, IslandPrivileges islandPrivilages) {
        Validate.notNull(uuid, "UUID cannot be null");
        Validate.notNull(islandPrivilages, "Privileges cannot be null");
        Validate.isTrue(!hasMember(uuid), "Member with same uuid is already added into island");
    }

    private void validatePrivileges(IslandPrivileges islandPrivilages) {
        if (islandPrivilages.isOwner()) {
            throw new ValidationException("You cant add member with owner privileges.");
        }
        if (islandPrivilages.isCoOwner()) {
            boolean islandHasCoOwner = members.stream().anyMatch(member -> member.getIslandPrivileges().isCoOwner());
            if (islandHasCoOwner) {
                throw new ValidationException("You cannot add member with co-owner privileges. Island already has co-owner");
            }
        }
    }

    public List<Member> getMembers() {
        return Lists.newArrayList(members);
    }

    public boolean hasMember(UUID uuid) {
        return members.stream()
                .anyMatch(member -> member.getUuid().equals(uuid));
    }

    public Optional<Member> getMember(UUID uuid) {
        return members.stream()
                .filter(member -> member.getUuid().equals(uuid))
                .findFirst();
    }

    public Optional<Member> getOwner() {
        return members.stream()
                .filter(member -> member.getIslandPrivileges().isOwner())
                .findAny();
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public void addFlag(IslandFlag islandFlag) {
        flags.add(islandFlag);
    }

    public void removeFlag(IslandFlag islandFlag) {
        flags.remove(islandFlag);
    }

    public boolean hasFlag(IslandFlag islandFlag) {
        return flags.contains(islandFlag);
    }

}
