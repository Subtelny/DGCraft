package pl.subtelny.components.validate;

import pl.subtelny.components.api.BeanContextException;
import pl.subtelny.components.prototype.BeanPrototype;

import java.util.List;
import java.util.stream.Collectors;

abstract class BeanValidator implements BeanValidateable {

    private final List<BeanPrototype> beanPrototypes;

    protected BeanValidator(List<BeanPrototype> beanPrototypes) {
        this.beanPrototypes = beanPrototypes;
    }

    public List<BeanPrototype> getBeanPrototypes() {
        return beanPrototypes;
    }

    public BeanPrototype getBeanPrototypeByClass(Class clazz) {
        return getBeanPrototypes().stream()
                .findAny()
                .orElseThrow(() -> BeanContextException.of("Not found prototype for class" + clazz));
    }

    public List<BeanPrototype> getBeanPrototypesByClass(Class clazz) {
        return beanPrototypes.stream()
                .filter(beanPrototype -> beanPrototype.isBeanPrototypeClass(clazz))
                .collect(Collectors.toList());
    }
}
