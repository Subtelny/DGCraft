package pl.subtelny.islands.island.action;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.util.CommandUtil;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.islands.commands.IslandInvitesCommand;
import pl.subtelny.islands.island.Island;
import pl.subtelny.islands.islander.IslanderQueryService;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.message.IslandMessages;

import java.util.UUID;

public class AskJoinIslandAction {

    private final IslanderQueryService islanderQueryService;

    private final IslandMessages messages;

    @Autowired
    public AskJoinIslandAction(IslanderQueryService islanderQueryService,
                               IslandMessages messages) {
        this.islanderQueryService = islanderQueryService;
        this.messages = messages;
    }

    public void perform(Island island, Player player) {
        askJoin(island, player);
        sendMessageToAskingPlayer(island, player);
        sendMessageToIslandOwner(island, player);
    }

    private void askJoin(Island island, Player player) {
        Islander islander = islanderQueryService.getIslander(player);
        island.askJoin(islander);
    }

    private void sendMessageToAskingPlayer(Island island, Player askingPlayer) {
        String islandName = island.getName();
        messages.sendTo(askingPlayer, "action.ask_join_island.request_sent", islandName);
    }

    private void sendMessageToIslandOwner(Island island, Player askingPlayer) {
        island.getOwner()
                .filter(islandMemberId -> Islander.ISLAND_MEMBER_TYPE.equals(islandMemberId.getType()))
                .map(islandMemberId -> UUID.fromString(islandMemberId.getValue()))
                .map(Bukkit::getPlayer)
                .ifPresent(player -> sendMessageToIslandOwner(player, askingPlayer));
    }

    private void sendMessageToIslandOwner(Player owner, Player askingPlayer) {
        String askingPlayerName = askingPlayer.getName();
        String message = messages.getColoredFormattedMessage("action.ask_join_island.request_received", askingPlayerName);

        TextComponent checkComponent = getCheckComponent();
        TextComponent component = new TextComponent(new ComponentBuilder()
                .append(message)
                .append(checkComponent)
                .create());

        owner.sendMessage(component);
    }

    private TextComponent getCheckComponent() {
        String message = messages.getColoredFormattedMessage("action.ask_join_island.request_received");
        String hoverMessage = messages.getColoredFormattedMessage("action.ask_join_island.request_received_hover");
        String invitesCommand = CommandUtil.getCommandPath(IslandInvitesCommand.class);

        TextComponent component = new TextComponent(TextComponent.fromLegacyText(message));
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, invitesCommand));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverMessage)));
        return component;
    }

}
