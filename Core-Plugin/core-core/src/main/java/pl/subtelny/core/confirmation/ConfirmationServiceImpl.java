package pl.subtelny.core.confirmation;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.subtelny.commands.api.PluginCommand;
import pl.subtelny.commands.api.util.CommandUtil;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.confirmation.ConfirmContextId;
import pl.subtelny.core.api.confirmation.ConfirmationRequest;
import pl.subtelny.core.api.confirmation.ConfirmationService;
import pl.subtelny.core.commands.ConfirmCommand;
import pl.subtelny.core.commands.RejectCommand;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.utilities.Validation;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;

@Component
public class ConfirmationServiceImpl implements ConfirmationService {

    private final CoreMessages messages;

    private final Cache<ConfirmContextId, Confirmation> confirmationCache = Caffeine.newBuilder()
            .expireAfterWrite(3, TimeUnit.MINUTES)
            .build();

    @Autowired
    public ConfirmationServiceImpl(CoreMessages messages) {
        this.messages = messages;
    }

    @Override
    public void confirm(Player player, ConfirmContextId confirmContextId) {
        answer(player, confirmContextId, "confirmation.confirmed", true);
    }

    @Override
    public void reject(Player player, ConfirmContextId confirmContextId) {
        answer(player, confirmContextId, "confirmation.rejected", false);
    }

    private void answer(Player player, ConfirmContextId confirmContextId, String messageKey, boolean state) {
        Confirmation confirmation = confirmationCache.getIfPresent(confirmContextId);
        basicValidation(player, confirmation);

        confirmationCache.invalidate(confirmContextId);
        messages.sendTo(player, messageKey);
        confirmation.notifyListener(state);
    }

    private void basicValidation(Player player, @Nullable Confirmation confirmation) {
        Validation.isTrue(confirmation != null, "confirmation.not_found");
        Validation.isTrue(confirmation.canConfirm(player), "confirmation.cant_confirm");
    }

    @Override
    public void makeConfirmation(ConfirmationRequest request) {
        ConfirmContextId confirmContextId = request.getConfirmContextId();
        Validation.isTrue(confirmationCache.getIfPresent(confirmContextId) == null, "confirmation.already_exists");
        Player player = request.getPlayer();
        confirmationCache.put(confirmContextId, new Confirmation(confirmContextId, player, request.getState()));
        sendMessage(player, request.getTitle(), confirmContextId);
    }

    private void sendMessage(Player player, String title, ConfirmContextId confirmContextId) {
        String message = messages.getColoredFormattedMessage("confirmation.message", title);
        TextComponent controls = getControls(confirmContextId);
        TextComponent component = new TextComponent(new ComponentBuilder()
                .append(message)
                .append(controls)
                .create());
        player.sendMessage(component);
    }

    private TextComponent getControls(ConfirmContextId confirmContextId) {
        String layout = messages.getColoredMessage("confirmation.buttons_layout");
        String[] splittedLayout = layout.split("(?:ACCEPT)|(?:REJECT)");
        Validate.isTrue(splittedLayout.length == 3, "Wrong confirmation button layout: " + layout);

        BaseComponent[] prefix = TextComponent.fromLegacyText(splittedLayout[0]);
        BaseComponent[] middle = TextComponent.fromLegacyText(splittedLayout[1]);
        BaseComponent[] suffix = TextComponent.fromLegacyText(splittedLayout[2]);
        TextComponent reject = getRejectComponent(confirmContextId);
        TextComponent confirm = getConfirmComponent(confirmContextId);

        BaseComponent[] baseComponents = new ComponentBuilder()
                .append(prefix)
                .append(confirm)
                .append(middle)
                .append(reject)
                .append(suffix)
                .create();
        return new TextComponent(baseComponents);
    }

    private TextComponent getRejectComponent(ConfirmContextId confirmContextId) {
        String rawRejectCommand = CommandUtil.getCommandPath(RejectCommand.class);
        String rejectCommand = rawRejectCommand + " " + confirmContextId.getInternal();
        String rejectHover = messages.getRawMessage("confirmation.reject_hover");
        String rejectButton = messages.getColoredMessage("confirmation.reject_button");
        return getControlComponent(rejectButton, rejectCommand, rejectHover);
    }

    private TextComponent getConfirmComponent(ConfirmContextId confirmContextId) {
        String rawConfirmCommand = CommandUtil.getCommandPath(ConfirmCommand.class);
        String confirmCommand = rawConfirmCommand + " " + confirmContextId.getInternal();
        String confirmHover = messages.getRawMessage("confirmation.confirm_hover");
        String confirmButton = messages.getColoredMessage("confirmation.confirm_button");
        return getControlComponent(confirmButton, confirmCommand, confirmHover);
    }

    private TextComponent getControlComponent(String messageKey, String command, String hover) {
        String message = messages.getColoredFormattedMessage(messageKey);
        TextComponent component = new TextComponent(TextComponent.fromLegacyText(message));
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hover)));
        return component;
    }

}
