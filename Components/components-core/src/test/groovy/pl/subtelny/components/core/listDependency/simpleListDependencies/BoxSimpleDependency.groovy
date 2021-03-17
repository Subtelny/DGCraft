package pl.subtelny.components.core.listDependency.simpleListDependencies

import pl.subtelny.components.core.api.Autowired
import pl.subtelny.components.core.api.Component

@Component
class BoxSimpleDependency {

    public final List<SimpleDependency> simpleDependencies;

    @Autowired
    BoxSimpleDependency(List<SimpleDependency> simpleDependencies) {
        this.simpleDependencies = simpleDependencies
    }

}
