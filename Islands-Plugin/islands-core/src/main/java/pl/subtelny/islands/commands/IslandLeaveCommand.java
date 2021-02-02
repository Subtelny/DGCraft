package pl.subtelny.islands.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.BaseCommand;
import pl.subtelny.commands.api.PluginSubCommand;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.cqrs.command.IslandCommandService;
import pl.subtelny.islands.island.cqrs.command.IslandMembershipRequest;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;

@PluginSubCommand(command = "wyjdz", aliases = "leave", mainCommand = IslandCommand.class)
public class IslandLeaveCommand extends BaseCommand {

    private final IslanderQueryService islanderService;

    private final IslandCommandService islandCommandService;

    @Autowired
    public IslandLeaveCommand(IslandMessages messages,
                              IslanderQueryService islanderService,
                              IslandCommandService islandCommandService) {
        super(messages);
        this.islanderService = islanderService;
        this.islandCommandService = islandCommandService;
    }

    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Islander islander = islanderService.getIslander(player);
        islander.getIslands()
                .stream()
                .findFirst()
                .ifPresentOrElse(islandId -> leaveIsland(islander, islandId), () -> notHaveIsland(player));
    }

    private void notHaveIsland(Player player) {
        getMessages().sendTo(player, "command.island.leave.not_have_island");
    }

    private void leaveIsland(Islander islander, IslandId islandId) {
        islandCommandService.membershipIsland(IslandMembershipRequest.leave(islander, islandId));
    }

    @Override
    public boolean isPlayerOnlyUsage() {
        return true;
    }
}
