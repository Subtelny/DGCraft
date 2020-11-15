package pl.subtelny.components.core.cyclicDependency.simpleCyclicDependency

import pl.subtelny.components.core.api.Autowired
import pl.subtelny.components.core.api.Component

@Component
class CyclicFirstComponent {

    @Autowired
    CyclicFirstComponent(CyclicSecondComponent component) {
    }
}
