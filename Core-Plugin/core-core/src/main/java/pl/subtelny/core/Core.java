package pl.subtelny.core;

import com.google.common.collect.Lists;
import pl.subtelny.commands.api.context.CommandsContext;
import pl.subtelny.components.core.BeanServiceImpl;
import pl.subtelny.components.core.api.BeanContext;
import pl.subtelny.core.configuration.Settings;
import pl.subtelny.plugin.DGPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Core extends DGPlugin {

    @Override
    public void onLoad() {
        BeanContext.initializeContext(new BeanServiceImpl());
        super.onLoad();
    }

    @Override
    public void onLoaded() {
        new Settings(this);
    }

    @Override
    public void onEnabled() {
    }

    @Override
    protected List<String> reflectionPaths() {
        String commandsPackage = "pl.subtelny.commands";
        return Lists.asList(commandsPackage, super.reflectionPaths().toArray(new String[0]));
    }

}
