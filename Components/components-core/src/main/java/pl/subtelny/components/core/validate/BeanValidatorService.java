package pl.subtelny.components.core.validate;

import pl.subtelny.components.core.prototype.BeanPrototype;

import java.util.Arrays;
import java.util.List;

public class BeanValidatorService {

    public void validate(List<BeanPrototype> beanPrototypes) {
        List<BeanValidateable> beanValidators = getBeanValidators(beanPrototypes);
        beanPrototypes.forEach(beanPrototype -> validateBean(beanPrototype, beanValidators));
    }

    private void validateBean(BeanPrototype beanPrototype, List<BeanValidateable> beanValidators) {
        beanValidators.forEach(beanValidate -> beanValidate.validate(beanPrototype));
    }

    private List<BeanValidateable> getBeanValidators(List<BeanPrototype> beanPrototypes) {
        return Arrays.asList(
                new BeanConstructorComponentsValidator(beanPrototypes),
                new BeanCyclicDependencyValidator(beanPrototypes)
        );
    }
}
