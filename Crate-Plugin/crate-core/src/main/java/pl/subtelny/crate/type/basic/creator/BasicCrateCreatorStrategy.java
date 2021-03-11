package pl.subtelny.crate.type.basic.creator;

import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.InventoryInfo;
import pl.subtelny.crate.api.creator.CrateCreatorStrategy;
import pl.subtelny.crate.api.type.basic.creator.BasicCrateCreatorRequest;
import pl.subtelny.crate.type.basic.BasicCrate;

@Component
public class BasicCrateCreatorStrategy implements CrateCreatorStrategy<BasicCrateCreatorRequest> {

    @Override
    public Crate create(BasicCrateCreatorRequest request) {
        InventoryInfo inventory = InventoryInfo.of(request.getTitle(), request.getInventorySize());
        return new BasicCrate(request.getCrateKey(), request.getPermission(), request.getContent(), inventory);
    }

    @Override
    public CrateType getType() {
        return BasicCrate.TYPE;
    }

}
