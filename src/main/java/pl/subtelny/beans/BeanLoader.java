package pl.subtelny.beans;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import pl.subtelny.beans.command.HeadCommand;
import pl.subtelny.beans.command.SubCommand;
import pl.subtelny.command.BaseCommand;
import pl.subtelny.command.Command;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public final class BeanLoader {

    private final String pathToScan;

    private Map<String, Object> preparedBeans;

    public BeanLoader(String pathToScan) {
        this.pathToScan = pathToScan;
        preparedBeans = new HashMap<>();
    }

    public void initializeBeans() {
        try {
            preparePrototypes();
            connectCommands();
        } catch (BeanPreparePrototypeException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        preparedBeans.forEach(BeanContext::addBean);
    }

    private void preparePrototypes() throws BeanPreparePrototypeException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Set<Class<?>> allComponents = loadClassComponentsFromPath();
        BeanPrototypeFactory factory = new BeanPrototypeFactory(allComponents);
        List<BeanPrototype> beanPrototypes = factory.preparePrototypes();
        for (BeanPrototype beanPrototype : beanPrototypes) {
            Object newInstance = loadBean(beanPrototype, beanPrototypes);
            String beanName = getBeanPrototypeName(beanPrototype);
            preparedBeans.put(beanName, newInstance);
        }
    }

    private void connectCommands() {
        List<Object> allCommands = getAllCommands();
        allCommands.forEach(command -> {
            if (command instanceof BaseCommand) {
                BaseCommand baseCommand = (BaseCommand) command;
                List<Object> subCommands = getSubCommandsRelatedToBaseCommand(baseCommand.getClass());
                subCommands
                        .forEach(subCommand -> {
                            SubCommand annotation = subCommand.getClass().getAnnotation(SubCommand.class);
                            baseCommand.registerSubCommand(annotation.command(), (Command) subCommand);
                        });
            }
        });
    }

    private List<Object> getAllCommands() {
        List<Object> commands = new ArrayList<>();
        preparedBeans.values().stream()
                .filter(value -> {
                    Annotation mainCommand = value.getClass().getAnnotation(HeadCommand.class);
                    Annotation subCommand = value.getClass().getAnnotation(SubCommand.class);
                    boolean isCommand = value instanceof Command;
                    return isCommand && (mainCommand != null || subCommand != null);
                })
                .forEach(commands::add);
        return commands;
    }

    private List<Object> getSubCommandsRelatedToBaseCommand(Class baseCommandClass) {
        List<Object> related = new ArrayList<>();
        for (Object value : preparedBeans.values()) {
            if (value instanceof Command) {
                SubCommand subCommand = value.getClass().getAnnotation(SubCommand.class);
                if (subCommand != null) {
                    if (subCommand.baseCommand() == baseCommandClass) {
                        related.add(value);
                    }
                }
            }
        }
        return related;
    }

    private Object loadBean(BeanPrototype beanPrototype, List<BeanPrototype> beanPrototypes) throws IllegalAccessException, InvocationTargetException, InstantiationException, BeanPreparePrototypeException {
        String beanName = getBeanPrototypeName(beanPrototype);
        if (preparedBeans.containsKey(beanName)) {
            return preparedBeans.get(beanName);
        }

        Parameter[] parameters = beanPrototype.getConstructor().getParameters();
        List<Object> initializedObjects = new ArrayList<>();

        for (Parameter parameter : parameters) {
            if (parameter.getParameterizedType() instanceof ParameterizedType) {
                Class<?> generic = BeanUtil.genericTypeFromParemeter(parameter);
                List<BeanPrototype> prototypes = BeanUtil.allPrototypesForClass(generic, beanPrototypes);

                List<Object> listObjects = new ArrayList<>();
                for (BeanPrototype prototype : prototypes) {
                    listObjects.add(loadBean(prototype, beanPrototypes));
                }
                initializedObjects.add(listObjects);
            } else {
                BeanPrototype prototype = BeanUtil.prototypeForClass(parameter.getType(), beanPrototypes).get();
                initializedObjects.add(loadBean(prototype, beanPrototypes));
            }
        }
        if (initializedObjects.size() == 0) {
            return beanPrototype.getConstructor().newInstance();
        }
        return beanPrototype.getConstructor().newInstance(initializedObjects.toArray());
    }

    private Set<Class<?>> loadClassComponentsFromPath() {
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setScanners(new SubTypesScanner(false));
        Reflections reflections = new Reflections(pathToScan);

        Set<Class<?>> list = new HashSet<>();
        list.addAll(reflections.getTypesAnnotatedWith(Component.class));
        list.addAll(reflections.getTypesAnnotatedWith(HeadCommand.class));
        list.addAll(reflections.getTypesAnnotatedWith(SubCommand.class));
        return list;
    }

    private String getBeanPrototypeName(BeanPrototype beanPrototype) {
        return beanPrototype.getComponent().getName();
    }

}
