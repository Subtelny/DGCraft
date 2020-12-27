package pl.subtelny.crate.prototype.page;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.condition.GlobalConditionStrategies;
import pl.subtelny.core.api.condition.GlobalRewardStrategies;
import pl.subtelny.crate.Crate;
import pl.subtelny.crate.api.CrateType;
import pl.subtelny.crate.api.query.request.GetCratePrototypeRequest;
import pl.subtelny.crate.model.crate.page.PageCrate;
import pl.subtelny.crate.api.prototype.ACratePrototypeCreator;
import pl.subtelny.crate.api.prototype.CratePrototypeFileParserStrategy;

import java.io.File;

@Component
public class PageCratePrototypeCreator extends ACratePrototypeCreator {

    @Autowired
    public PageCratePrototypeCreator(GlobalConditionStrategies conditionStrategies,
                                     GlobalRewardStrategies rewardStrategies) {
        super(conditionStrategies, rewardStrategies);
    }

    @Override
    protected CratePrototypeFileParserStrategy getStrategy(GetCratePrototypeRequest request) {
        File file = request.getFile();
        return new PageCratePrototypeFileParserStrategy(file,
                getConditionStrategy(file, request.getConditionParsers()),
                getCostConditionStrategy(file, request.getCostConditionParsers()),
                getRewardParsers(file, request.getRewardParsers()),
                request.getCratePrefix(),
                request.getPlugin());
    }

    @Override
    public CrateType getType() {
        return PageCrate.PAGE_TYPE;
    }
}
