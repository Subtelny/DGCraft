package pl.subtelny.utilities.messages;

import org.bukkit.command.CommandSender;
import pl.subtelny.utilities.MessageUtil;

public abstract class Messages {

    public void sendTo(CommandSender sender, String messageKey, Object... format) {
        String message = getRawMessage(messageKey);
        String formattedMessage = String.format(message, format);
        MessageUtil.message(sender, formattedMessage);
    }

    public void sendTo(CommandSender sender, String messageKey) {
        MessageUtil.message(sender, getRawMessage(messageKey));
    }

    public abstract String getRawMessage(String path);

    public abstract void reloadMessages();

}
