package pl.subtelny.gui.messages;

import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;

@Component
public class CrateMessagesInitializer implements DependencyActivator {

    private final CrateMessages messages;

    @Autowired
    public CrateMessagesInitializer(CrateMessages messages) {
        this.messages = messages;
    }

    @Override
    public void activate(Plugin plugin) {
        messages.initMessages(plugin);
    }
}
