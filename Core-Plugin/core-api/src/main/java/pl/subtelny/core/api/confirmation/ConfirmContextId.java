package pl.subtelny.core.api.confirmation;

import pl.subtelny.utilities.identity.BasicIdentity;

public class ConfirmContextId extends BasicIdentity<String> {

    private ConfirmContextId(String id) {
        super(id);
    }

    public static ConfirmContextId of(String contextId) {
        return new ConfirmContextId(contextId);
    }
}
