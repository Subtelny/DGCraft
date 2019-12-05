package pl.subtelny.islands.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.UUID;

public class Member {

    private final UUID uuid;

    private final IslandPrivileges islandPrivileges;

    public Member(UUID uuid, IslandPrivileges islandPrivileges) {
        this.uuid = uuid;
        this.islandPrivileges = islandPrivileges;
    }

    public UUID getUuid() {
        return uuid;
    }

    public IslandPrivileges getIslandPrivileges() {
        return islandPrivileges;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Member)) {
            return false;
        }

        Member member = (Member) obj;
        return new EqualsBuilder()
                .append(uuid, member.uuid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uuid)
                .toHashCode();
    }
}
