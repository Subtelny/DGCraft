package pl.subtelny.components.validate;

import pl.subtelny.components.prototype.BeanPrototype;

import java.util.List;

abstract class BeanValidator implements BeanValidateable {

    private final List<BeanPrototype> beanPrototypes;

    protected BeanValidator(List<BeanPrototype> beanPrototypes) {
        this.beanPrototypes = beanPrototypes;
    }

    public List<BeanPrototype> getBeanPrototypes() {
        return beanPrototypes;
    }
}
