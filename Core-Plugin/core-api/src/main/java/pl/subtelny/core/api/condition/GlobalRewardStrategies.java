package pl.subtelny.core.api.condition;

import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.List;

public interface GlobalRewardStrategies {

    List<PathAbstractFileParserStrategy<? extends Reward>> getRewardParsers(File file);

}
