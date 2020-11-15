package pl.subtelny.components.core.cyclicDependency.listDependency

import pl.subtelny.components.core.api.Autowired
import pl.subtelny.components.core.api.Component

@Component
class ListFirstComponent {

    private final List<ListComponent> components

    @Autowired
    ListFirstComponent(List<ListComponent> components) {
        this.components = components
    }

    List<ListComponent> getComponents() {
        return components
    }

}
