package pl.subtelny.islands.island.module;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.islands.island.Island;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class IslandModules implements DependencyActivator {

    private final List<IslandModuleCreator> islandModuleCreators;

    private final List<IslandModule<Island>> islandModules = new ArrayList<>();

    @Autowired
    public IslandModules(List<IslandModuleCreator> islandModuleCreators) {
        this.islandModuleCreators = islandModuleCreators;
    }

    public List<IslandModule<Island>> getIslandModules() {
        return Collections.unmodifiableList(islandModules);
    }

    @Override
    public void activate(Plugin plugin) {
        islandModuleCreators.stream()
                .map(IslandModuleCreator::createModule)
                .forEach(islandModules::add);
    }
}
