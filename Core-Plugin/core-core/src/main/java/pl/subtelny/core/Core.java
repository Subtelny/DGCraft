package pl.subtelny.core;

import pl.subtelny.components.BeanServiceImpl;
import pl.subtelny.components.api.BeanContext;
import pl.subtelny.core.configuration.Settings;
import pl.subtelny.plugin.DGPlugin;

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

}
