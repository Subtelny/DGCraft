package pl.subtelny.crate.api.service;

import org.bukkit.plugin.Plugin;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class InitializeCrateRequest {

    private final File file;

    private final Plugin plugin;

    private final String prefixKey;

    private final List<PathAbstractFileParserStrategy<? extends Reward>> rewardFileParserStrategies;

    public InitializeCrateRequest(File file,
                                  Plugin plugin,
                                  String prefixKey,
                                  List<PathAbstractFileParserStrategy<? extends Reward>> rewardFileParserStrategies) {
        this.file = file;
        this.plugin = plugin;
        this.prefixKey = prefixKey;
        this.rewardFileParserStrategies = rewardFileParserStrategies;
    }

    public static InitializeCrateRequest of(File dir, Plugin plugin, String prefixCrateKey) {
        return of(dir, plugin, prefixCrateKey, new ArrayList<>());
    }

    public static InitializeCrateRequest of(File dir,
                                            Plugin plugin,
                                            String prefixCrateKey,
                                            List<PathAbstractFileParserStrategy<? extends Reward>> rewardFileParserStrategies) {
        return new InitializeCrateRequest(dir, plugin, prefixCrateKey, rewardFileParserStrategies);
    }

    public File getFile() {
        return file;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getPrefixKey() {
        return prefixKey;
    }

    public List<PathAbstractFileParserStrategy<? extends Reward>> getRewardFileParserStrategies() {
        return rewardFileParserStrategies;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InitializeCrateRequest request = (InitializeCrateRequest) o;
        return Objects.equals(file, request.file) && Objects.equals(plugin, request.plugin) && Objects.equals(prefixKey, request.prefixKey) && Objects.equals(rewardFileParserStrategies, request.rewardFileParserStrategies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, plugin, prefixKey, rewardFileParserStrategies);
    }
}
