package pl.subtelny.utilities.log;

import org.bukkit.Bukkit;

import java.util.logging.Logger;

public final class Log {

    private static Logger logger = Bukkit.getLogger();

    public static void info(String message) {
        logger.info("[INFO]:" + message);
    }

    public static void warning(String message) {
        logger.warning("[WARNING}: " + message);
    }

}
