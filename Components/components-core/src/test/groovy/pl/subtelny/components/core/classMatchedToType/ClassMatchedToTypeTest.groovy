package pl.subtelny.components.core.classMatchedToType

import pl.subtelny.components.core.ComponentUtil
import spock.lang.Specification

class ClassMatchedToTypeTest extends Specification {

    def "implementation not matched to it interface type"() {
        given:
        def clazz = ComponentInterface.class
        def type = ComponentSecond.class.getDeclaredConstructors()[0].getGenericParameterTypes()[0]

        when:
        def matched = ComponentUtil.classMatchedToType(clazz, type)

        then:
        !matched
    }

    def "interface matched to implementation type"() {
        given:
        def clazz = ComponentFirst.class
        def type = ComponentSecond.class.getDeclaredConstructors()[0].getGenericParameterTypes()[0]

        when:
        def matched = ComponentUtil.classMatchedToType(clazz, type)

        then:
        matched
    }

}
