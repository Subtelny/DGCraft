package pl.subtelny.islands.skyblockisland.creator;

import pl.subtelny.islands.islander.model.IslandCoordinates;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.skyblockisland.schematic.SkyblockIslandSchematicOption;

import java.util.Optional;

public class CreateSkyblockIslandRequest {

    private final Islander islander;

    private IslandCoordinates coordinates;

    private SkyblockIslandSchematicOption option;

    private IslandCreateStateListener stateListener;

    private CreateSkyblockIslandRequest(Islander islander, IslandCoordinates coordinates, SkyblockIslandSchematicOption option, IslandCreateStateListener stateListener) {
        this.islander = islander;
        this.coordinates = coordinates;
        this.option = option;
        this.stateListener = stateListener;
    }

    public Islander getIslander() {
        return islander;
    }

    public Optional<IslandCoordinates> getCoordinates() {
        return Optional.ofNullable(coordinates);
    }

    public Optional<SkyblockIslandSchematicOption> getOption() {
        return Optional.ofNullable(option);
    }

    public Optional<IslandCreateStateListener> getStateListener() {
        return Optional.ofNullable(stateListener);
    }

    public static Builder builder(Islander islander) {
        return new Builder(islander);
    }

    public static class Builder {

        private final Islander islander;

        private IslandCoordinates coordinates;

        private SkyblockIslandSchematicOption option;

        private IslandCreateStateListener stateListener;

        public Builder(Islander islander) {
            this.islander = islander;
        }

        public Builder coordinates(IslandCoordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public Builder option(SkyblockIslandSchematicOption option) {
            this.option = option;
            return this;
        }

        public Builder stateListener(IslandCreateStateListener stateListener) {
            this.stateListener = stateListener;
            return this;
        }

        public CreateSkyblockIslandRequest build() {
            return new CreateSkyblockIslandRequest(islander, coordinates, option, stateListener);
        }
    }

}
