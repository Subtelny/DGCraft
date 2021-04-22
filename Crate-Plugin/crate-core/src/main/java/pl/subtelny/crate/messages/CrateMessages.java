package pl.subtelny.crate.messages;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.utilities.messages.DefaultMessages;

@Component
public class CrateMessages extends DefaultMessages {

    private static CrateMessages instance;

    @Autowired
    public CrateMessages() {
        instance = this;
    }

    public static CrateMessages get() {
        return instance;
    }

}
