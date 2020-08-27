package pl.subtelny.islands.island;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.List;

@Component
public class IslandSaveService {

    private final List<IslandSaver> islandSavers;

    @Autowired
    public IslandSaveService(List<IslandSaver> islandSavers) {
        this.islandSavers = islandSavers;
    }

    public void saveIsland(Island island) {
        IslandSaver islandSaver = findIslandSaver(island);
        islandSaver.saveIsland(island);
        //TODO
        //zrobić coś tutaj kurwa
    }

    private IslandSaver findIslandSaver(Island island) {
        return islandSavers.stream()
                .filter(islandSaver -> islandSaver.getIslandClass().isInstance(island))
                .findAny()
                .orElseThrow(() -> ValidationException.of("islandSave.not_found_saver", island.getId()));
    }

}
