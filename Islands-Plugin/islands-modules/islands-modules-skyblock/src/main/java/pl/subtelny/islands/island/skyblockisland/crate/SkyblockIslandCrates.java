package pl.subtelny.islands.island.skyblockisland.crate;

import org.bukkit.entity.Player;
import pl.subtelny.crate.api.Crate;
import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.prototype.CratePrototype;
import pl.subtelny.crate.api.service.CrateService;
import pl.subtelny.crate.api.service.InitializeCratesRequest;
import pl.subtelny.islands.Islands;
import pl.subtelny.islands.crate.type.config.ConfigCrateCreatorRequest;
import pl.subtelny.islands.crate.type.config.ConfigCratePrototype;
import pl.subtelny.islands.crate.type.invites.InvitesCrateCreatorRequest;
import pl.subtelny.islands.crate.type.invites.InvitesCratePrototype;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.island.IslandType;
import pl.subtelny.islands.island.crate.IslandCrates;
import pl.subtelny.utilities.Validation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SkyblockIslandCrates implements IslandCrates {

    private final IslandType islandType;

    private final List<CrateKey> crateKeys = new ArrayList<>();

    private final CrateService crateService;

    private File dir;

    public SkyblockIslandCrates(IslandType islandType, CrateService crateService) {
        this.islandType = islandType;
        this.crateService = crateService;
    }

    public void initialize(File dir) {
        Validation.isFalse(dir == null, "Not found dir to load crates from");
        this.dir = dir;
        List<CrateKey> crateKeys = crateService.initializeCrates(InitializeCratesRequest.of(dir, Islands.PLUGIN, islandType.getInternal()));
        this.crateKeys.addAll(crateKeys);
    }

    @Override
    public void openInvites(Player player, Island island) {
        CratePrototype cratePrototype = getCratePrototype(InvitesCratePrototype.TYPE.getType());
        InvitesCrateCreatorRequest request = InvitesCrateCreatorRequest.withIsland((InvitesCrateCreatorRequest) cratePrototype.toCrateCreatorRequest(), island);
        Crate crate = crateService.getCrate(request);
        crate.open(player);
    }

    @Override
    public void openSettings(Player player, Island island) {
        CratePrototype cratePrototype = getCratePrototype(ConfigCratePrototype.TYPE.getType());
        ConfigCrateCreatorRequest request = ConfigCrateCreatorRequest.withIsland((ConfigCrateCreatorRequest) cratePrototype.toCrateCreatorRequest(), island);
        Crate crate = crateService.getCrate(request);
        crate.open(player);
    }

    @Override
    public void openCreateIsland(Player player) {
        Crate crate = crateService.getCrate(prepareCrateKey(InvitesCratePrototype.TYPE.getType()));
        crate.open(player);
    }

    @Override
    public void openMain(Player player) {

    }

    @Override
    public void reload() {
        crateService.unitializeCrates(crateKeys);
        crateKeys.clear();
        initialize(dir);
    }

    public CratePrototype getCratePrototype(String key) {
        return crateService.getCratePrototype(prepareCrateKey(key));
    }

    private CrateKey prepareCrateKey(String key) {
        return CrateKey.of(islandType.getInternal() + "-" + key.toLowerCase(), Islands.PLUGIN);
    }

}
