package pl.subtelny.components.core.cyclicDependency.listCyclicDependency

import pl.subtelny.components.core.api.Autowired
import pl.subtelny.components.core.api.Component

@Component
class ListCyclicFirstComponent {

    @Autowired
    ListCyclicFirstComponent(List<ListCyclicFirstComponent> components) {
    }
}
