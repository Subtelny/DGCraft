package pl.subtelny.crate.api.command;

import org.bukkit.plugin.Plugin;
import pl.subtelny.crate.api.CrateId;
import pl.subtelny.crate.api.prototype.CratePrototype;

import java.util.Collection;

public interface CrateCommandService {

    void closeAll();

    void closeAll(Plugin plugin);

    void closeAll(Collection<CrateId> crateIds);

    void closeAll(CrateId crateId);

    void register(CratePrototype cratePrototype);

    void unregister(CrateId crateId);

    void unregisterAll(Collection<CrateId> crateIds);

    void unregisterAll(Plugin plugin);

}
