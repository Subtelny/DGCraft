package pl.subtelny.gui.commands;

import org.bukkit.command.CommandSender;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.gui.GUI;
import pl.subtelny.gui.api.crate.CrateLoadRequest;
import pl.subtelny.gui.api.crate.CratesLoaderService;
import pl.subtelny.gui.messages.CrateMessages;
import pl.subtelny.utilities.file.FileUtil;

import java.io.File;

@PluginSubCommand(command = "reload", mainCommand = CrateDevCommand.class)
public class CrateDevReloadCommand extends BaseCommand {

    private final CratesLoaderService cratesLoaderService;

    @Autowired
    public CrateDevReloadCommand(CrateMessages messages, CratesLoaderService cratesLoaderService) {
        super(messages);
        this.cratesLoaderService = cratesLoaderService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        reloadGuis();
        getMessages().sendTo(sender, "command.cratedev.reload.reloaded");
    }

    private void reloadGuis() {
        cratesLoaderService.unloadAllCrates(GUI.plugin);
        File dir = FileUtil.getFile(GUI.plugin, "guis");
        CrateLoadRequest request = CrateLoadRequest.newBuilder(dir)
                .setPlugin(GUI.plugin)
                .build();
        cratesLoaderService.loadCrates(request);
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return false;
    }
}
