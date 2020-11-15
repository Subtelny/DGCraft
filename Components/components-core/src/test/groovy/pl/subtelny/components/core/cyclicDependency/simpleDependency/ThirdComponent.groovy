package pl.subtelny.components.core.cyclicDependency.simpleDependency

import pl.subtelny.components.core.api.Autowired
import pl.subtelny.components.core.api.Component

@Component
class ThirdComponent {

    private final FourthComponent fourthComponent

    @Autowired
    ThirdComponent(FourthComponent fourthComponent) {
        this.fourthComponent = fourthComponent
    }

    FourthComponent getFourthComponent() {
        return fourthComponent
    }
}
