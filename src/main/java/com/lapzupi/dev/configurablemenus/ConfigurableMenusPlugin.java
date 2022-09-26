package com.lapzupi.dev.configurablemenus;

import co.aikar.commands.PaperCommandManager;
import com.github.sarhatabaot.kraken.core.logging.LoggerUtil;
import com.lapzupi.dev.configurablemenus.addons.AddonManager;
import com.lapzupi.dev.configurablemenus.commands.AddonsCommand;
import com.lapzupi.dev.configurablemenus.commands.MenuCommand;
import com.lapzupi.dev.configurablemenus.commands.ReloadCommand;
import com.lapzupi.dev.configurablemenus.config.MenuConfigurate;
import com.lapzupi.dev.configurablemenus.config.SettingsConfigurate;
import com.lapzupi.dev.configurablemenus.menu.MenuManager;
import com.lapzupi.dev.configurablemenus.menu.model.Menu;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;

import java.io.File;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class ConfigurableMenusPlugin extends JavaPlugin {
    private static final String MENUS = "menus";
    private SettingsConfigurate settings;
    private AddonManager addonManager;
    private MenuManager menuManager;
    private BukkitAudiences adventure;


    public @NonNull BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {
        try {
            this.settings = new SettingsConfigurate(this);
        } catch (ConfigurateException e) {
            //
        }
        this.adventure = BukkitAudiences.create(this);

        this.addonManager = new AddonManager(this);

        if (this.settings.extractedDefaultMenus()) {
            extractDefaultMenus();
        }

        this.addonManager.load();
        this.menuManager = new MenuManager(this);



        loadMenus();

        registerCommands();
    }

    private void extractDefaultMenus() {
        File menuFolder = new File(getDataFolder(), MENUS);
        for(String path: getFileNamesInJarMenusFolder()) {
            debug("Path %s".formatted(path));
            final String[] split = path.split("/");
            final String resourcePath = split[0];
            final String fileName = split[1];

            FileUtil.saveFileFromJar(this,resourcePath + File.separator, fileName, menuFolder);
        }
    }

    private @NotNull List<String> getFileNamesInJarMenusFolder() {
        List<String> fileNames = new ArrayList<>();
        CodeSource src = ConfigurableMenusPlugin.class.getProtectionDomain().getCodeSource();
        if (src != null) {
            URL jar = src.getLocation();
            try(ZipInputStream zip = new ZipInputStream(jar.openStream())) {
                while (true) {
                    ZipEntry e = zip.getNextEntry();
                    if (e == null)
                        break;
                    String name = e.getName();
                    if (name.startsWith("menus/") && name.endsWith(".conf")) {
                        fileNames.add(name);
                    }
                }
            } catch (IOException e) {
                //
            }
        }
        return fileNames;
    }

    private void registerCommands() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(this);
        paperCommandManager.getCommandCompletions().registerCompletion(MENUS, c -> menuManager.getMenuIds());
        paperCommandManager.registerCommand(new MenuCommand(this));
        paperCommandManager.registerCommand(new ReloadCommand(this));
        paperCommandManager.registerCommand(new AddonsCommand(this));
    }


    public void onReload() {
        this.addonManager.load();
        loadMenus();
    }

    public void reloadMenus() {
        loadMenus();
    }

    private void loadMenus() {
        for (String fileName : getFileNamesInMenusFolder()) {
            try {
                MenuConfigurate menuConfigurate = new MenuConfigurate(this, "", fileName);
                Menu<?> menu = menuConfigurate.getMenu();
                if (menuManager.containsConfigurate(menu.getId())) {
                    MenuConfigurate existing = menuManager.getConfigurate(menu.getId());
                    existing.reloadConfig();
                    menuManager.registerMenu(existing.getMenu());
                } else {
                    menuManager.registerMenu(menu);
                    menuManager.registerConfigurate(menu.getId(), menuConfigurate);
                }
            } catch (ConfigurateException e) {
                LoggerUtil.init(ConfigurableMenusPlugin.class);
                LoggerUtil.logSevereException(e);
            }
        }
    }

    private List<String> getFileNamesInMenusFolder() {
        File file = new File(getDataFolder(), MENUS);
        if (file.isDirectory()) {
            String[] fileNameList = file.list((dir, name) -> name.endsWith(".conf"));
            if (fileNameList == null)
                return Collections.emptyList();
            return List.of(fileNameList);
        }
        return Collections.emptyList();
    }

    @Override
    public void onDisable() {
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public void debug(final String message) {
        if (settings.isDebug()) {
            getLogger().info(() -> "DEBUG %s".formatted(message));
        }
    }

    public void debug(final String message, final Throwable throwable) {
        if (settings.isDebug()) {
            getLogger().log(Level.INFO, "DEBUG %s".formatted(message), throwable);
        }
    }

    public AddonManager getAddonManager() {
        return addonManager;
    }
}
