package pl.subtelny.utilities.reward.command;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.utilities.reward.Reward;

public class CommandReward implements Reward {

    private final String command;

    public CommandReward(String command) {
        this.command = command;
    }

    @Override
    public void admitReward(Player player) {
        ConsoleCommandSender sender = Bukkit.getConsoleSender();
        String command = this.command.replace("%s", player.getName());
        Bukkit.dispatchCommand(sender, command);
    }

}
