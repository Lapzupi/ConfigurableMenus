package com.lapzupi.dev.configurablemenus;

import co.aikar.commands.PaperCommandManager;
import com.lapzupi.dev.configurablemenus.config.MenuConfigurate;
import com.lapzupi.dev.configurablemenus.hooks.HdbWrapper;
import com.lapzupi.dev.configurablemenus.hooks.ItemsAdderWrapper;
import com.lapzupi.dev.configurablemenus.hooks.OraxenWrapper;
import com.lapzupi.dev.configurablemenus.menu.MenuManager;
import com.lapzupi.dev.configurablemenus.menu.model.Menu;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.ConfigurateException;

import java.io.File;
import java.util.Collections;
import java.util.List;

public final class ConfigurableMenusPlugin extends JavaPlugin {
    private MenuManager menuManager;
    private HdbWrapper hdbWrapper;
    private ItemsAdderWrapper itemsAdderWrapper;
    private OraxenWrapper oraxenWrapper;

    @Override
    public void onEnable() {
        registerListeners();

        this.menuManager = new MenuManager();
        loadMenus();

        PaperCommandManager paperCommandManager = new PaperCommandManager(this);
        paperCommandManager.getCommandCompletions().registerCompletion("menus", c -> menuManager.getMenuIds());
        paperCommandManager.registerCommand(new MenuCommand(this));
        // Plugin startup logic
    }

    public void onReload() {
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
                //
            }
        }
    }

    private List<String> getFileNamesInMenusFolder() {
        File file = new File(getDataFolder(), "menus");
        if (file.isDirectory()) {
            String[] fileNameList = file.list((dir, name) -> name.endsWith(".yml"));
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

    public HdbWrapper getHdbWrapper() {
        return hdbWrapper;
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        if (Bukkit.getPluginManager().getPlugin("HeadDatabase") != null) {
            this.hdbWrapper = new HdbWrapper(this);
            pm.registerEvents(this.hdbWrapper, this);
        }
        if (Bukkit.getPluginManager().getPlugin("Oraxen") != null) {
            this.oraxenWrapper = new OraxenWrapper(this);
            pm.registerEvents(this.oraxenWrapper, this);
        }
        if (Bukkit.getPluginManager().getPlugin("ItemsAdder") != null) {
            this.itemsAdderWrapper = new ItemsAdderWrapper(this);
            pm.registerEvents(this.itemsAdderWrapper, this);
        }
    }

    public ItemsAdderWrapper getItemsAdderWrapper() {
        return itemsAdderWrapper;
    }

    public OraxenWrapper getOraxenWrapper() {
        return oraxenWrapper;
    }

    public void reload() {
        //reload the menus.
        getLogger().info("Reloaded!");
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }
}
