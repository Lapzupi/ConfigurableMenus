package com.lapzupi.dev.configurablemenus.hooks;

import com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * @author sarhatabaot
 */
public class ItemsAdderWrapper implements Listener {
    private final ConfigurableMenusPlugin plugin;

    public ItemsAdderWrapper(final ConfigurableMenusPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemsAdderLoad(ItemsAdderLoadDataEvent event) {
        this.plugin.getLogger().info("Detected that ItemsAdder has loaded, reloading...");
        this.plugin.reload();
    }

    public ItemStack getItem(final String namespace, final String itemName) {
        try {
            return CustomStack.getInstance(namespace + ":" + itemName).getItemStack();
        } catch (NullPointerException e) {
            this.plugin.debug("ItemsAdder not loaded yet, returning temporary AIR itemstack.");
            return new ItemStack(Material.AIR);
        }
    }
}
