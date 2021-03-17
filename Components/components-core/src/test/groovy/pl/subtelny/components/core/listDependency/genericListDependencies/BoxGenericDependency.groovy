package pl.subtelny.components.core.listDependency.genericListDependencies

import pl.subtelny.components.core.api.Autowired
import pl.subtelny.components.core.api.Component

@Component
class BoxGenericDependency {

    public final List<GenericDependency> genericDependencies;

    @Autowired
    BoxGenericDependency(List<GenericDependency> genericDependencies) {
        this.genericDependencies = genericDependencies
    }
}
