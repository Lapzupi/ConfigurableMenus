package com.lapzupi.dev.configurablemenus.menu;

import com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin;
import com.lapzupi.dev.configurablemenus.hooks.HdbWrapper;
import com.lapzupi.dev.configurablemenus.hooks.ItemsAdderWrapper;
import com.lapzupi.dev.configurablemenus.hooks.OraxenWrapper;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

/**
 * @author sarhatabaot
 */
public class ItemUtils {
    private static final String PLUGIN_NAME = "ConfigurableMenus";

    public static ItemStack getFromHdb(final String id) throws NullPointerException {
        ConfigurableMenusPlugin plugin = (ConfigurableMenusPlugin) Bukkit.getPluginManager().getPlugin(PLUGIN_NAME);
        HdbWrapper hdbWrapper = plugin.getHdbWrapper();
        HeadDatabaseAPI api = hdbWrapper.getApi();
        return api.getItemHead(id);
    }

    public static ItemStack getFromOraxen(final String id) {
        ConfigurableMenusPlugin plugin = (ConfigurableMenusPlugin) Bukkit.getPluginManager().getPlugin(PLUGIN_NAME);
        OraxenWrapper oraxenWrapper = plugin.getOraxenWrapper();
        return oraxenWrapper.getItem(id);
    }

    public static ItemStack getFromItemsAdder(final String namespace, final String itemName) {
        ConfigurableMenusPlugin plugin = (ConfigurableMenusPlugin) Bukkit.getPluginManager().getPlugin(PLUGIN_NAME);
        ItemsAdderWrapper itemsAdderWrapper = plugin.getItemsAdderWrapper();
        return itemsAdderWrapper.getItem(namespace,itemName);
    }
}
