package pl.subtelny.components.core.cyclicDependency.simpleDependency

import pl.subtelny.components.core.api.Autowired
import pl.subtelny.components.core.api.Component

@Component
class FirstComponent {

    private final SecondComponent secondComponent

    @Autowired
    FirstComponent(SecondComponent secondComponent) {
        this.secondComponent = secondComponent
    }

    SecondComponent getSecondComponent() {
        return secondComponent
    }
}
