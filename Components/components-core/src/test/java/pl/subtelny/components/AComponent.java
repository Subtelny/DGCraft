package pl.subtelny.components;

import pl.subtelny.components.api.Autowired;
import pl.subtelny.components.api.Component;

@Component
public class AComponent {

    private final TestComponent testComponent;

    @Autowired
    public AComponent(TestComponent testComponent) {
        this.testComponent = testComponent;
    }
}
