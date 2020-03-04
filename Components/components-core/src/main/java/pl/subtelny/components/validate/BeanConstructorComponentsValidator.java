package pl.subtelny.components.validate;

import com.google.common.collect.Lists;
import pl.subtelny.components.api.BeanContextException;
import pl.subtelny.components.prototype.BeanPrototype;

import java.util.List;
import java.util.stream.Collectors;

public class BeanConstructorComponentsValidator extends BeanValidator {

    public BeanConstructorComponentsValidator(List<BeanPrototype> beanPrototypes) {
        super(beanPrototypes);
    }

    @Override
    public void validate(BeanPrototype beanPrototype) {
        List<Class> constructorParameters = Lists.newArrayList(beanPrototype.getConstructor().getParameterTypes());
        List<Class> beanPrototypesClasses = getBeanPrototypes().stream().map(BeanPrototype::getClazz).collect(Collectors.toList());

        boolean hasOnlyBeanComponents = beanPrototypesClasses.containsAll(constructorParameters);
        if (!hasOnlyBeanComponents) {
            String message = String.format("Not all constructor parameters are components in %s", beanPrototype.getClazz().getName());
            throw BeanContextException.of(message);
        }
    }

}
