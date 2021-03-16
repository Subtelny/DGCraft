package pl.subtelny.utilities.messages;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import net.md_5.bungee.api.chat.BaseComponent;

public interface Messageable {

    Messageable EMPTY = new Messageable() {
        @Override
        public void sendMessage(String message) {
            //Noop
        }

        @Override
        public void sendMessage(BaseComponent component) {
            //Noop
        }
    };

    void sendMessage(String message);

    void sendMessage(@NotNull BaseComponent component);

}
