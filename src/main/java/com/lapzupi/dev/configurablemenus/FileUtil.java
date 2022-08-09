package com.lapzupi.dev.configurablemenus;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @author sarhatabaot
 */
public class FileUtil {
    public static void saveFileFromJar(JavaPlugin plugin, final String resourcePath, final String fileName, final File folder) {
        File file = new File(folder, fileName);

        if (!file.exists()) {
            plugin.saveResource(resourcePath + fileName, false);
        }

    }
}
