package pl.subtelny.islands.settings;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.IntStream;

public class SkyblockIslandExtendOptions {

    private final List<SkyblockIslandExtend> islandExtends;

    private SkyblockIslandExtendOptions(List<SkyblockIslandExtend> islandExtends) {
        this.islandExtends = islandExtends;
    }

    public SkyblockIslandExtend getIslandExtend(int extendLevel) {
        return islandExtends.get(extendLevel);
    }

    public List<SkyblockIslandExtend> getIslandExtends() {
        return Lists.newArrayList(islandExtends);
    }

    public int getSumOfExtendSizeToLevel(int extendLevel) {
        return IntStream.range(0, extendLevel - 1)
                .map(i -> getIslandExtend(i).getExtendSize())
                .sum();
    }

    public int getTotalExtendSize() {
        return islandExtends.stream().mapToInt(SkyblockIslandExtend::getExtendSize).sum();
    }

    public static class Builder {

        private List<SkyblockIslandExtend> islandExtends = Lists.newArrayList();

        public Builder() {
            islandExtends.add(new SkyblockIslandExtend(0));
        }

        public Builder addExtend(int extendSize) {
            islandExtends.add(new SkyblockIslandExtend(extendSize));
            return this;
        }

        public SkyblockIslandExtendOptions build() {
            return new SkyblockIslandExtendOptions(islandExtends);
        }

    }

}
