package pl.subtelny.core.api.condition;

import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.condition.CostCondition;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;

import java.io.File;
import java.util.List;

public interface GlobalConditionStrategies {

    List<PathAbstractFileParserStrategy<? extends Condition>> getGlobalConditionStrategies(File file);

    List<PathAbstractFileParserStrategy<? extends CostCondition>> getGlobalCostConditionStrategies(File file);

}
