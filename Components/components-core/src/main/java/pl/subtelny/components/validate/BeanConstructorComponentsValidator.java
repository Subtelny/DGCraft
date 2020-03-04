package pl.subtelny.components.validate;

import pl.subtelny.components.api.BeanContextException;
import pl.subtelny.components.prototype.BeanPrototype;
import pl.subtelny.components.util.BeanUtil;
import pl.subtelny.utilities.collection.CollectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class BeanConstructorComponentsValidator extends BeanValidator {

    public BeanConstructorComponentsValidator(List<BeanPrototype> beanPrototypes) {
        super(beanPrototypes);
    }

    @Override
    public void validate(BeanPrototype beanPrototype) {
        Constructor constructor = beanPrototype.getConstructor();
        validateConstructorComponents(constructor);
        validateClassCollection(constructor);
    }

    private void validateConstructorComponents(Constructor constructor) {
        Arrays.stream(constructor.getParameters())
                .forEach(parameter -> validateConstructorParameter(constructor, parameter));
    }

    private void validateConstructorParameter(Constructor constructor, Parameter parameter) {
        Class<?> type = parameter.getType();
        if (CollectionUtil.isCollection(type)) {
            type = BeanUtil.genericTypeFromParemeter(parameter);
        }
        if (!isMatchAnyBean(type)) {
            String format = String.format("Not found bean %s in constructor %s", type.getName(), constructor.getName());
            throw BeanContextException.of(format);
        }
    }

    private boolean isMatchAnyBean(Class clazz) {
        return getBeanPrototypesByClass(clazz).size() > 0;
    }

    private void validateClassCollection(Constructor constructor) {
        Stream<Class> parameterTypes = Arrays.stream(constructor.getParameterTypes());
        parameterTypes.forEach(aClass -> {
            if (!isValidClassCollection(aClass)) {
                String format = String.format("Found more than one bean for class %s in constructor %s", aClass.getName(), constructor.getName());
                throw BeanContextException.of(format);
            }
        });
    }

    private boolean isValidClassCollection(Class clazz) {
        if (hasMultipleBeans(clazz)) {
            return CollectionUtil.isCollection(clazz);
        }
        return true;
    }

    private boolean hasMultipleBeans(Class clazz) {
        long count = getBeanPrototypesByClass(clazz).size();
        return count > 1;
    }

}
