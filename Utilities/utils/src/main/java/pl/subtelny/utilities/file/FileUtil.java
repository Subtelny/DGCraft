package pl.subtelny.utilities.file;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class FileUtil {

    public static File getFile(Plugin plugin, String fileName) {
        File dataFolder = plugin.getDataFolder();
        return new File(dataFolder, fileName);
    }

    public static File copyFile(Plugin plugin, String file) {
        File configFile = new File(plugin.getDataFolder(), file);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            FileUtil.copy(plugin.getResource(file), configFile);
        }
        return configFile;
    }

    public static File copyFile(File dataFolder, InputStream resource, String file) {
        File configFile = new File(dataFolder, file);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            FileUtil.copy(resource, configFile);
        }
        return configFile;
    }

    private static void copy(InputStream in, File file) {
        if (in == null)
            return;
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
