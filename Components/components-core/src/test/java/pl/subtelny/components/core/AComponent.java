package pl.subtelny.components.core;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;

import java.util.Set;

@Component
public class AComponent {

    private final Set<BInterface> bInterface;

    private final TestComponent testComponent;

    @Autowired
    public AComponent(Set<BInterface> bInterface, TestComponent testComponent) {
        this.bInterface = bInterface;
        this.testComponent = testComponent;
    }
}
