package pl.subtelny.components.core.cyclicDependency

import pl.subtelny.components.core.api.ComponentException
import pl.subtelny.components.core.ComponentsLoader
import pl.subtelny.components.core.cyclicDependency.listCyclicDependency.ListCyclicFirstComponent
import pl.subtelny.components.core.cyclicDependency.listDependency.ListFirstComponent
import pl.subtelny.components.core.cyclicDependency.simpleCyclicDependency.CyclicFirstComponent
import pl.subtelny.components.core.cyclicDependency.simpleDependency.FirstComponent
import pl.subtelny.components.core.cyclicDependency.simpleDependency.SecondComponent
import pl.subtelny.components.core.cyclicDependency.simpleDependency.ThirdComponent
import spock.lang.Specification

class CyclicDependenciesTest extends Specification {

    def "find cyclic dependency in two related to each other classes"() {
        given:
            def classLoaders = Arrays.asList(CyclicFirstComponent.class.getClassLoader())
            def pathToScan = CyclicFirstComponent.class.getPackageName()
            ComponentsLoader loader = new ComponentsLoader(classLoaders, pathToScan)

        when:
            loader.loadComponents()

        then:
            thrown ComponentException
    }

    def "find cyclic dependency in list component"() {
        given:
            def classLoaders = Arrays.asList(ListCyclicFirstComponent.class.getClassLoader())
            def pathToScan = ListCyclicFirstComponent.class.getPackageName()
            ComponentsLoader loader = new ComponentsLoader(classLoaders, pathToScan)

        when:
            loader.loadComponents()

        then:
            thrown ComponentException
    }

    def "properly add three components into List dependency"() {
        given:
            def classLoaders = Arrays.asList(ListFirstComponent.class.getClassLoader())
            def pathToScan = ListFirstComponent.class.getPackageName()
            ComponentsLoader loader = new ComponentsLoader(classLoaders, pathToScan)

        when:
            def components = loader.loadComponents()

        then:
            components.size() == 4
            ((ListFirstComponent) components.get(ListFirstComponent.class)).getComponents().size() == 3
    }

    def "simple autowired dependencies"() {
        given:
            def classLoaders = Arrays.asList(FirstComponent.class.getClassLoader())
            def pathToScan = FirstComponent.class.getPackageName()
            ComponentsLoader loader = new ComponentsLoader(classLoaders, pathToScan)

        when:
            def components = loader.loadComponents()

        then:
            components.size() == 4
            ((FirstComponent) components.get(FirstComponent.class)).getSecondComponent() != null
            ((SecondComponent) components.get(SecondComponent.class)).getThirdComponent() != null
            ((SecondComponent) components.get(SecondComponent.class)).getFourthComponent() != null
            ((ThirdComponent) components.get(ThirdComponent.class)).getFourthComponent() != null
    }

}
