package com.lapzupi.dev.configurablemenus.addons;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

/**
 * @author sarhatabaot
 */
public abstract class ItemAddon implements Addon{

    /**
     * @param id id of the ItemStack
     * @return The ItemStack via the id
     */
    public abstract ItemStack getItemStack(final String id);


    @Override
    public final String toString() {
        return "ItemAddon[prefix: %s, author: %s, version: %s]".formatted(getPrefix(), getAuthor(), getVersion());
    }

}
