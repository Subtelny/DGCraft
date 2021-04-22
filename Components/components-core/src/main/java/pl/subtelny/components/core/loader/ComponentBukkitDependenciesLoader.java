package pl.subtelny.components.core.loader;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.CommandsInitializer;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core.api.plugin.ComponentPlugin;
import pl.subtelny.components.core.plugin.DependencyActivatorComparator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ComponentBukkitDependenciesLoader {

    private final Collection<ComponentObjectInfo> components;

    public ComponentBukkitDependenciesLoader(Collection<ComponentObjectInfo> components) {
        this.components = components;
    }

    public void load() {
        groupByPlugin().forEach(this::load);
        loadDependencyActivators(components);
    }

    private void load(ComponentPlugin componentPlugin, List<ComponentObjectInfo> componentObjectInfos) {
        loadCommands(componentPlugin, componentObjectInfos);
        loadListeners(componentPlugin, componentObjectInfos);
    }

    private void loadDependencyActivators(Collection<ComponentObjectInfo> componentObjectInfos) {
        List<TypedComponentObjectInfo<DependencyActivator>> activators = getTypedComponents(DependencyActivator.class, componentObjectInfos);
        DependencyActivatorComparator dependencyActivatorComparator = new DependencyActivatorComparator();
        activators.stream()
                .sorted((t1, t2) -> dependencyActivatorComparator.compare(t1.object, t2.object))
                .forEach(component -> component.object.activate(component.componentPlugin));
    }

    private void loadCommands(ComponentPlugin componentPlugin, List<ComponentObjectInfo> componentObjectInfos) {
        List<BaseCommand> commands = getComponents(BaseCommand.class, componentObjectInfos);
        CommandsInitializer commandsInitializer = new CommandsInitializer(componentPlugin, commands);
        commandsInitializer.registerCommands();
    }

    private void loadListeners(ComponentPlugin componentPlugin, List<ComponentObjectInfo> componentObjectInfos) {
        List<Listener> listeners = getComponents(Listener.class, componentObjectInfos);
        listeners.forEach(listener -> registerListener(componentPlugin, listener));
    }

    private void registerListener(ComponentPlugin componentPlugin, Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, componentPlugin);
    }

    public Map<ComponentPlugin, List<ComponentObjectInfo>> groupByPlugin() {
        return components
                .stream()
                .collect(Collectors.groupingBy(ComponentObjectInfo::getComponentPlugin));
    }

    public <T> List<T> getComponents(Class<T> clazz, List<ComponentObjectInfo> componentObjectInfos) {
        return componentObjectInfos.stream()
                .map(ComponentObjectInfo::getObject)
                .filter(component -> clazz.isAssignableFrom(component.getClass()))
                .map(bean -> (T) bean)
                .collect(Collectors.toList());
    }

    public <T> List<TypedComponentObjectInfo<T>> getTypedComponents(Class<T> clazz, Collection<ComponentObjectInfo> componentObjectInfos) {
        return componentObjectInfos.stream()
                .filter(component -> clazz.isAssignableFrom(component.getObject().getClass()))
                .map(bean -> new TypedComponentObjectInfo<T>(bean.getComponentPlugin(), (T) bean.getObject()))
                .collect(Collectors.toList());
    }

    private static class TypedComponentObjectInfo<T> {

        private final ComponentPlugin componentPlugin;

        private final T object;

        private TypedComponentObjectInfo(ComponentPlugin componentPlugin, T object) {
            this.componentPlugin = componentPlugin;
            this.object = object;
        }
    }

}
