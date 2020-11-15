package pl.subtelny.components.core.cyclicDependency.simpleDependency

import pl.subtelny.components.core.api.Autowired
import pl.subtelny.components.core.api.Component

@Component
class SecondComponent {

    private final ThirdComponent thirdComponent

    private final FourthComponent fourthComponent

    @Autowired
    SecondComponent(ThirdComponent thirdComponent, FourthComponent fourthComponent) {
        this.thirdComponent = thirdComponent
        this.fourthComponent = fourthComponent
    }

    ThirdComponent getThirdComponent() {
        return thirdComponent
    }

    FourthComponent getFourthComponent() {
        return fourthComponent
    }
}
