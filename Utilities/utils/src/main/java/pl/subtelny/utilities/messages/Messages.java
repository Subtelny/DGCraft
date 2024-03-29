package pl.subtelny.utilities.messages;

import org.bukkit.command.CommandSender;

public abstract class Messages {

    public void sendTo(Messageable messageable, String messageKey, Object... format) {
        String message = getRawMessage(messageKey);
        String formattedMessage = String.format(message, format);
        MessageUtil.message(messageable, formattedMessage);
    }

    public void sendTo(CommandSender sender, String messageKey, Object... format) {
        String message = getRawMessage(messageKey);
        String formattedMessage = String.format(message, format);
        MessageUtil.message(sender, formattedMessage);
    }

    public void sendTo(CommandSender sender, String messageKey) {
        MessageUtil.message(sender, getRawMessage(messageKey));
    }

    public String getColoredFormattedMessage(String messageKey, Object... format) {
        String formattedMessage = getFormattedMessage(messageKey, format);
        return getColoredMessage(formattedMessage);
    }

    public String getFormattedMessage(String messageKey, Object... format) {
        String message = getRawMessage(messageKey);
        return String.format(message, format);
    }

    public String getColoredMessage(String messageKey) {
        String message = getRawMessage(messageKey);
        return MessageUtil.color(message);
    }

    public abstract String getRawMessage(String path);

    public abstract void reloadMessages();

}
