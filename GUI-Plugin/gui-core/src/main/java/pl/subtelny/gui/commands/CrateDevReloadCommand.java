package pl.subtelny.gui.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.gui.GUI;
import pl.subtelny.gui.api.crate.repository.CrateRepository;
import pl.subtelny.gui.crate.settings.CratesLoader;
import pl.subtelny.gui.messages.CrateMessages;
import pl.subtelny.gui.session.PlayerCrateSessionService;

import java.io.File;

@PluginSubCommand(command = "reload", mainCommand = CrateDevCommand.class)
public class CrateDevReloadCommand extends BaseCommand {

    private final CrateRepository crateRepository;

    private final CratesLoader cratesLoader;

    private final PlayerCrateSessionService sessionService;

    @Autowired
    public CrateDevReloadCommand(CrateMessages messages, CrateRepository crateRepository, CratesLoader cratesLoader, PlayerCrateSessionService sessionService) {
        super(messages);
        this.crateRepository = crateRepository;
        this.cratesLoader = cratesLoader;
        this.sessionService = sessionService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        sessionService.closeAllSessions(GUI.plugin);
        crateRepository.unregisterAll(GUI.plugin);
        File dataFolder = GUI.plugin.getDataFolder();
        File dir = new File(dataFolder, "guis");
        cratesLoader.loadAllCratesFromDirectory(dir).forEach(crateRepository::registerCrate);
        getMessages().sendTo(sender, "command.cratedev.reload.reloaded");
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
