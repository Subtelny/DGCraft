package pl.subtelny.components.core.listDependency

import pl.subtelny.components.core.ComponentClassLoaderInfo
import pl.subtelny.components.core.listDependency.genericListDependencies.BoxGenericDependency
import pl.subtelny.components.core.listDependency.simpleListDependencies.BoxSimpleDependency
import pl.subtelny.components.core.loader.ComponentsLoader
import spock.lang.Specification

class ListDependenciesTest extends Specification {

    def "component should contain all three implementations of listener in list parameter"() {
        given:
        def classLoaders = Arrays.asList(new ComponentClassLoaderInfo(null, BoxSimpleDependency.class.getClassLoader()))
        def pathToScan = BoxSimpleDependency.class.getPackageName()
        ComponentsLoader loader = new ComponentsLoader(classLoaders, pathToScan)

        when:
        def components = loader.loadComponents()
        BoxSimpleDependency boxDependency = components.get(BoxSimpleDependency.class).getObject() as BoxSimpleDependency

        then:
        boxDependency.simpleDependencies.size() == 2
    }

    def "component should contain all implementations of listener with generic type"() {
        given:
        def classLoaders = Arrays.asList(new ComponentClassLoaderInfo(null, BoxGenericDependency.class.getClassLoader()))
        def pathToScan = BoxGenericDependency.class.getPackageName()
        ComponentsLoader loader = new ComponentsLoader(classLoaders, pathToScan)

        when:
        def components = loader.loadComponents()
        BoxGenericDependency boxDependency = components.get(BoxGenericDependency.class).getObject() as BoxGenericDependency

        then:
        boxDependency.genericDependencies.size() == 1
    }


}
