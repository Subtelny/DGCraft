package pl.subtelny.utilities.messages;

import org.bukkit.command.CommandSender;
import pl.subtelny.utilities.MessageUtil;

public class Messages {

    private final MessagesStorage storage;

    public Messages(MessagesStorage storage) {
        this.storage = storage;
    }

    public void sendTo(CommandSender sender, String messageKey, Object... format) {
        String message = get(messageKey);
        String formattedMessage = String.format(message, format);
        MessageUtil.message(sender, formattedMessage);
    }

    public void sendTo(CommandSender sender, String messageKey) {
        MessageUtil.message(sender, get(messageKey));
    }

    public String get(String path) {
        return storage.get(path);
    }

}
