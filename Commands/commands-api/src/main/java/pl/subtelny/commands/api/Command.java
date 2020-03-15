package pl.subtelny.commands.api;

import org.bukkit.command.CommandSender;

public interface Command {

    boolean executeCommand(CommandSender sender, String[] args);

    boolean isPlayerOnlyUsage();

}
