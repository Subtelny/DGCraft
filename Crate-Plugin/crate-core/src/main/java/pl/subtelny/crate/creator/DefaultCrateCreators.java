package pl.subtelny.crate.creator;

import pl.subtelny.crate.api.creator.CrateCreator;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.type.basic.BasicCrateCreator;
import pl.subtelny.crate.type.basic.BasicCratePrototype;
import pl.subtelny.crate.type.paged.PagedCrateCreator;
import pl.subtelny.crate.type.paged.PagedCratePrototype;
import pl.subtelny.crate.type.personal.PersonalCrateCreator;
import pl.subtelny.crate.type.personal.PersonalCratePrototype;

import java.util.Map;

public class DefaultCrateCreators {

    public static final Map<Class<? extends CratePrototype>, CrateCreator<?>> DEFAULT_CREATORS =
            Map.of(
                    BasicCratePrototype.class, new BasicCrateCreator(),
                    PagedCratePrototype.class, new PagedCrateCreator(),
                    PersonalCratePrototype.class, new PersonalCrateCreator()
            );

    public static <T extends CratePrototype> CrateCreator<T> getCrateCreator(Class<? extends CratePrototype> clazz) {
        CrateCreator<T> crateCreator = (CrateCreator<T>) DEFAULT_CREATORS.get(clazz);
        if (crateCreator == null) {
            throw new IllegalStateException("Not found crateCreator for class " + clazz.getName());
        }
        return crateCreator;
    }

}
