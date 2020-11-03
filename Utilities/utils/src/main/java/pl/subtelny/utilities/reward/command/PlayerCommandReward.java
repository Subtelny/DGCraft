package pl.subtelny.utilities.reward.command;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.utilities.reward.Reward;

public class PlayerCommandReward implements Reward {

    private final String command;

    public PlayerCommandReward(String command) {
        this.command = command;
    }

    @Override
    public void admitReward(Player player) {
        String command = this.command.replace("%s", player.getName());
        Bukkit.dispatchCommand(player, command);
    }

}
