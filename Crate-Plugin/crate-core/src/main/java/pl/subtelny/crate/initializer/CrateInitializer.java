package pl.subtelny.crate.initializer;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.crate.api.CrateInventory;
import pl.subtelny.crate.api.CrateKey;
import pl.subtelny.crate.api.service.InitializeCrateRequest;
import pl.subtelny.crate.service.CrateRegistrationService;
import pl.subtelny.crate.service.RegisterCratePrototypeRequest;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Component
public class CrateInitializer {

    private final CrateRegistrationService crateRegistrationService;

    @Autowired
    public CrateInitializer(CrateRegistrationService crateRegistrationService) {
        this.crateRegistrationService = crateRegistrationService;
    }

    public void uninitialize(List<CrateKey> crateKeys) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getOpenInventory().getTopInventory() instanceof CrateInventory)
                .filter(player -> crateKeys.contains(((CrateInventory) player.getOpenInventory().getTopInventory()).getCrate()))
                .forEach(player -> player.closeInventory(InventoryCloseEvent.Reason.CANT_USE));
        crateKeys.forEach(crateRegistrationService::unregisterCratePrototype);
    }

    public CrateKey initializeFromFile(InitializeCrateRequest request) {
        RegisterCratePrototypeRequest registerRequest = RegisterCratePrototypeRequest.of(
                request.getFile(),
                request.getPlugin(),
                request.getPrefixKey(),
                request.getRewardFileParserStrategies()
        );
        return crateRegistrationService.registerCratePrototype(registerRequest);
    }

    public void initializeFromDir(File dir, Plugin plugin, String keyPrefix) {
        validateFile(dir);
        File[] files = dir.listFiles();
        if (files != null) {
            Arrays.stream(files)
                    .forEach(file -> initializeFromFile(file, plugin, keyPrefix));
        }
    }

    private void initializeFromFile(File file, Plugin plugin, String keyPrefix) {
        crateRegistrationService.registerCratePrototype(RegisterCratePrototypeRequest.of(file, plugin, keyPrefix));
    }

    private void validateFile(File file) {
        if (!file.isDirectory()) {
            throw new IllegalStateException("File is not directory");
        }
    }

}
