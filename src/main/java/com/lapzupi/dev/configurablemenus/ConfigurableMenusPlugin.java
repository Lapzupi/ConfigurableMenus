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
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.ConfigurateException;

import java.io.File;

import java.util.Collections;
import java.util.List;

public final class ConfigurableMenusPlugin extends JavaPlugin {
    private SettingsConfigurate settings;
    private AddonManager addonManager;
    private MenuManager menuManager;


    @Override
    public void onEnable() {
        try {
            this.settings = new SettingsConfigurate(this);
        } catch (ConfigurateException e) {
            //
        }

        if(this.settings.isLoadExampleMenus()) {

        }
//        registerListeners();
        this.addonManager = new AddonManager(this);
        this.addonManager.load();
        this.menuManager = new MenuManager(this);
        loadMenus();

        registerCommands();
        // Plugin startup logic
    }

    private void registerCommands() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(this);
        paperCommandManager.getCommandCompletions().registerCompletion("menus", c -> menuManager.getMenuIds());
        paperCommandManager.registerCommand(new MenuCommand(this));
        paperCommandManager.registerCommand(new ReloadCommand(this));
        paperCommandManager.registerCommand(new AddonsCommand(this));
    }


    public void onReload() {
        loadMenus();
        this.addonManager.load();
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
        File file = new File(getDataFolder(), "menus");
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
        // Plugin shutdown logic
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public void debug(final String message) {
        if(settings.isDebug()) {
            getLogger().info(() -> "DEBUG %s".formatted(message));
        }
    }

    public AddonManager getAddonManager() {
        return addonManager;
    }
}
