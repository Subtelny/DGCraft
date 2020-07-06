package pl.subtelny.utilities.condition.permission;

import org.bukkit.entity.Player;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.messages.MessageKey;

import java.util.Objects;

public class PermissionCondition implements Condition {

    private final String permission;

    public PermissionCondition(String permission) {
        this.permission = permission;
    }

    @Override
    public boolean satisfiesCondition(Player player) {
        return player.hasPermission(permission);
    }

    @Override
    public MessageKey getMessageKey() {
        return new MessageKey("condition.permission." + permission + ".not_satisfied");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionCondition that = (PermissionCondition) o;
        return Objects.equals(permission, that.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permission);
    }
}
