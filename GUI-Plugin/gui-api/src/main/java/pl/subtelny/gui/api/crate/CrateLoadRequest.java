package pl.subtelny.gui.api.crate;

import org.bukkit.plugin.Plugin;
import pl.subtelny.utilities.file.PathAbstractFileParserStrategy;
import pl.subtelny.utilities.reward.Reward;

import java.io.File;
import java.util.*;

public class CrateLoadRequest {

    private final File file;

    private final List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers;

    private final Plugin plugin;

    private final String prefix;

    private CrateLoadRequest(File file, List<PathAbstractFileParserStrategy<? extends Reward>> additionalRewardParsers, Plugin plugin, String prefix) {
        this.file = file;
        this.rewardParsers = additionalRewardParsers;
        this.plugin = plugin;
        this.prefix = prefix;
    }

    public File getFile() {
        return file;
    }

    public List<PathAbstractFileParserStrategy<? extends Reward>> getRewardParsers() {
        return rewardParsers;
    }

    public Optional<Plugin> getPlugin() {
        return Optional.ofNullable(plugin);
    }

    public Optional<String> getPrefix() {
        return Optional.ofNullable(prefix);
    }

    public static Builder newBuilder(File file) {
        return new Builder(file);
    }

    public static class Builder {

        private final File file;

        private final List<PathAbstractFileParserStrategy<? extends Reward>> rewardParsers = new ArrayList<>();

        private Plugin plugin;

        private String prefix;

        public Builder(File file) {
            this.file = file;
        }

        public Builder addRewardParser(PathAbstractFileParserStrategy<? extends Reward> parser) {
            this.rewardParsers.add(parser);
            return this;
        }

        public Builder setPlugin(Plugin plugin) {
            this.plugin = plugin;
            return this;
        }

        public Builder setPrefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public CrateLoadRequest build() {
            return new CrateLoadRequest(file, rewardParsers, plugin, prefix);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrateLoadRequest request = (CrateLoadRequest) o;
        return Objects.equals(file, request.file) &&
                Objects.equals(rewardParsers, request.rewardParsers) &&
                Objects.equals(plugin, request.plugin) &&
                Objects.equals(prefix, request.prefix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rewardParsers, plugin, prefix);
    }
}
