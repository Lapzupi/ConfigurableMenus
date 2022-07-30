package com.lapzupi.dev.configurablemenus.hooks;

import com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin;
import io.th0rgal.oraxen.items.OraxenItems;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * @author sarhatabaot
 */
public class OraxenWrapper implements Listener {
    private ConfigurableMenusPlugin plugin;

    public OraxenWrapper(final ConfigurableMenusPlugin plugin) {
        this.plugin = plugin;
    }

    public ItemStack getItem(final String id) {
        if(OraxenItems.getItemById(id) != null) {
          return OraxenItems.getItemById(id).build();
        }
        throw new NullPointerException("No oraxen item with id %s".formatted(id));
    }
}
