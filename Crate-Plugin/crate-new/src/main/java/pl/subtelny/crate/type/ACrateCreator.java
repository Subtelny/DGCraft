package pl.subtelny.crate.type;

import pl.subtelny.crate.creator.CrateCreator;
import pl.subtelny.crate.prototype.CratePrototype;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.permission.PermissionCondition;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class ACrateCreator<T extends CratePrototype> implements CrateCreator<T> {

    private final Class<T> clazz;

    protected ACrateCreator() {
        this.clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Class<T> getClazz() {
        return clazz;
    }

    protected List<Condition> getUseConditions(CratePrototype createPrototype) {
        String permission = createPrototype.getPermission();
        return Optional.ofNullable(permission)
                .map(PermissionCondition::new)
                .stream()
                .collect(Collectors.toList());
    }

}
