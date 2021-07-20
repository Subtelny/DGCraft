package pl.subtelny.islands.module;

import org.bukkit.Bukkit;
import org.bukkit.World;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core.api.plugin.ComponentPlugin;
import pl.subtelny.islands.api.Island;
import pl.subtelny.islands.api.IslandType;
import pl.subtelny.islands.api.module.IslandModule;
import pl.subtelny.islands.api.module.IslandModules;

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
    public <T extends Island> Optional<IslandModule<T>> findIslandModule(Class<T> island) {
        return Optional.empty();
    }

    @Override
    public Optional<IslandModule<Island>> findIslandModule(IslandType islandType) {
        return islandModules.stream()
                .filter(islandModule -> islandModule.getIslandType().equals(islandType))
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
    public void activate(ComponentPlugin componentPlugin) {
        IslandModulesInitializer modulesInitializer = new IslandModulesInitializer(islandModuleCreators);
        List<IslandModule<Island>> islandModules = getIslandModules(componentPlugin, modulesInitializer);
        this.islandModules.addAll(islandModules);
    }

    private List<IslandModule<Island>> getIslandModules(ComponentPlugin componentPlugin, IslandModulesInitializer modulesInitializer) {
        try {
            return modulesInitializer.initializeModules(componentPlugin);
        } catch (Exception e) {
            Bukkit.getServer().shutdown();
            throw e;
        }
    }
}
