package com.lapzupi.dev.configurablemenus;

import com.lapzupi.dev.configurablemenus.hooks.HdbWrapper;
import com.lapzupi.dev.configurablemenus.hooks.ItemsAdderWrapper;
import com.lapzupi.dev.configurablemenus.hooks.OraxenWrapper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ConfigurableMenusPlugin extends JavaPlugin {
    private HdbWrapper hdbWrapper;
    private ItemsAdderWrapper itemsAdderWrapper;
    private OraxenWrapper oraxenWrapper;

    @Override
    public void onEnable() {

        registerListeners();

        // Plugin startup logic
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
        if(Bukkit.getPluginManager().getPlugin("HeadDatabase") != null) {
            this.hdbWrapper = new HdbWrapper(this);
            pm.registerEvents(this.hdbWrapper, this);
        }
        if(Bukkit.getPluginManager().getPlugin("Oraxen") != null) {
            this.oraxenWrapper = new OraxenWrapper(this);
            pm.registerEvents(this.oraxenWrapper, this);
        }
        if(Bukkit.getPluginManager().getPlugin("ItemsAdder") != null) {
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
}
