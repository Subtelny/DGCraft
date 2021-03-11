package pl.subtelny.crate.service;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.loader.CratePrototypeLoadRequest;
import pl.subtelny.crate.loader.CratePrototypeLoader;
import pl.subtelny.crate.parser.DefaultCrateParsers;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.storage.CrateStorage;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;

@Component
public class CrateRegistrationService {

    private final DefaultCrateParsers defaultCrateParsers;

    private final CratePrototypeLoader cratePrototypeLoader;

    private final CrateStorage storage;

    @Autowired
    public CrateRegistrationService(DefaultCrateParsers defaultCrateParsers,
                                    CratePrototypeLoader cratePrototypeLoader,
                                    CrateStorage storage) {
        this.defaultCrateParsers = defaultCrateParsers;
        this.cratePrototypeLoader = cratePrototypeLoader;
        this.storage = storage;
    }

    public void unregisterCratePrototype(CrateKey crateKey) {
        storage.removeCratePrototype(crateKey);
        storage.removeGlobalCrate(crateKey);
    }

    public CrateKey registerCratePrototype(RegisterCratePrototypeRequest request) {
        CratePrototypeLoadRequest cratePrototypeLoadRequest = getCratePrototypeLoadRequest(request);
        CratePrototype prototype = cratePrototypeLoader.load(cratePrototypeLoadRequest);
        storage.addCratePrototype(prototype);
        return prototype.getCrateKey();
    }

    private CratePrototypeLoadRequest getCratePrototypeLoadRequest(RegisterCratePrototypeRequest request) {
        File file = request.getFile();
        Iterable<PathAbstractFileParserStrategy<? extends Condition>> conditions = getConditions(request, file);
        Iterable<PathAbstractFileParserStrategy<? extends CostCondition>> costConditions = getCostConditions(request, file);
        Iterable<PathAbstractFileParserStrategy<? extends Reward>> rewards = getRewards(request, file);
        return CratePrototypeLoadRequest.of(
                file,
                request.getCrateKeyPrefix(),
                request.getPlugin(),
                Lists.newArrayList(conditions),
                Lists.newArrayList(costConditions),
                Lists.newArrayList(rewards)
        );
    }

    private Iterable<PathAbstractFileParserStrategy<? extends Reward>> getRewards(RegisterCratePrototypeRequest request, File file) {
        return Iterables.concat(request.getRewardFileParserStrategies(), defaultCrateParsers.getDefaultRewardParsers(file));
    }

    private Iterable<PathAbstractFileParserStrategy<? extends CostCondition>> getCostConditions(RegisterCratePrototypeRequest request, File file) {
        return Iterables.concat(request.getCostConditionFileParserStrategies(), defaultCrateParsers.getDefaultCostConditionParsers(file));
    }

    private Iterable<PathAbstractFileParserStrategy<? extends Condition>> getConditions(RegisterCratePrototypeRequest request, File file) {
        return Iterables.concat(request.getConditionFileParserStrategies(), defaultCrateParsers.getDefaultConditionParsers(file));
    }

}
