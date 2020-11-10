package pl.subtelny.gui.messages;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.gui.GUI;

@Component
public class CrateMessagesInitializer implements DependencyActivator {

    private final CrateMessages messages;

    @Autowired
    public CrateMessagesInitializer(CrateMessages messages) {
        this.messages = messages;
    }

    @Override
    public void activate() {
        messages.initMessages(GUI.plugin);
    }
}
