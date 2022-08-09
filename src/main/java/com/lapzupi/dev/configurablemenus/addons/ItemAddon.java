package com.lapzupi.dev.configurablemenus.addons;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

/**
 * @author sarhatabaot
 */
public abstract class ItemAddon{
    public abstract ItemStack getItemStack(final String id);

    /**
     * @return The prefix used by the addon: "playerhead:id" or "base64:id"
     */
    public abstract String getPrefix();

    public abstract String getPluginName();

    public boolean canRegister() {
        return (Bukkit.getPluginManager().getPlugin(getPluginName()) != null);
    }

    public abstract String getAuthor();

    public abstract String getVersion();

    @Override
    public final String toString() {
        return "ItemAddon[prefix: %s, author: %s, version: %s]".formatted(getPrefix(), getAuthor(), getVersion());
    }

}
