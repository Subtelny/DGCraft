package pl.subtelny.crate.api.prototype;

import java.util.List;

public interface CratePrototypeLoader {

    List<CratePrototype> loadCratePrototypes(CratePrototypeLoadRequest request);

    CratePrototype loadCratePrototype(CratePrototypeLoadRequest request);

    CratePrototype loadCratePrototype(CratePrototypeLoadGlobalRequest request);

}
