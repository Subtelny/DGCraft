package pl.subtelny.islands.island.module;

import org.bukkit.Bukkit;
import org.bukkit.World;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class IslandModulesImpl implements DependencyActivator, IslandModules {

    private final List<IslandModuleCreator<Island>> islandModuleCreators;

    private final List<IslandModule<Island>> islandModules = new ArrayList<>();

    @Autowired
    public IslandModulesImpl(List<IslandModuleCreator<Island>> islandModuleCreators) {
        this.islandModuleCreators = islandModuleCreators;
    }

    @Override
    public Optional<IslandModule<Island>> findIslandModule(IslandType islandType) {
        return islandModules.stream()
                .filter(islandModule -> islandModule.getType().equals(islandType))
                .findFirst();
    }

    @Override
    public Optional<IslandModule<Island>> findIslandModule(World world) {
        return islandModules.stream()
                .filter(islandModule -> islandModule.getWorld().equals(world))
                .findFirst();
    }

    @Override
    public List<IslandModule<Island>> getIslandModules() {
        return Collections.unmodifiableList(islandModules);
    }

    @Override
    public void activate() {
        IslandModulesInitializer modulesInitializer = new IslandModulesInitializer(islandModuleCreators);
        List<IslandModule<Island>> islandModules = getIslandModules(modulesInitializer);
        this.islandModules.addAll(islandModules);
    }

    private List<IslandModule<Island>> getIslandModules(IslandModulesInitializer modulesInitializer) {
        try {
            return modulesInitializer.initializeModules(Islands.plugin);
        } catch (Exception e) {
            Bukkit.getServer().shutdown();
            throw e;
        }
    }
}
