package com.lapzupi.dev.configurablemenus.addons;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

/**
 * @author sarhatabaot
 */
public abstract class ItemAddon implements Addon{
    private static final String EMPTY_URL = "";

    /**
     * @param id id of the ItemStack
     * @return The ItemStack via the id
     */
    public abstract ItemStack getItemStack(final String id);



    /**
     * Can this addon be registered.
     */
    @Override
    public boolean canRegister() {
        return (Bukkit.getPluginManager().getPlugin(getPluginName()) != null);
    }

    /**
     * @return Return a link to the addons project page or download link
     */
    @Override
    public String getUrl() {
        return EMPTY_URL;
    }

    @Override
    public final String toString() {
        return "ItemAddon[prefix: %s, author: %s, version: %s]".formatted(getPrefix(), getAuthor(), getVersion());
    }

}
