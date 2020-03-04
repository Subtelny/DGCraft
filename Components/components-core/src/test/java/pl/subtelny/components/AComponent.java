package pl.subtelny.components;

import pl.subtelny.components.api.Autowired;
import pl.subtelny.components.api.Component;

import java.util.List;
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
