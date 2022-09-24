package com.lapzupi.dev.configurablemenus.addons;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

/**
 * @author sarhatabaot
 */
public abstract class ItemAddon{
    /**
     * @param id id of the ItemStack
     * @return The ItemStack via the id
     */
    public abstract ItemStack getItemStack(final String id);

    /**
     * @return The prefix used by the addon: "playerhead:id" or "base64:id in `prefix:id`"
     */
    public abstract String getPrefix();

    /**
     * @return The dependant plugin name, if there isn't one, you can set this to null.
     * You should override {@link #canRegister() canRegister() method} if there is no dependant plugin.
     */
    public abstract String getPluginName();


    /**
     * Can this addon be registered.
     */
    public boolean canRegister() {
        return (Bukkit.getPluginManager().getPlugin(getPluginName()) != null);
    }

    /**
     * @return The author's name
     */
    public abstract String getAuthor();

    /**
     * @return The addon's version
     */
    public abstract String getVersion();

    /**
     * @return Return a link to the addons project page or download link
     */
    public String getUrl() {
        return "";
    }

    @Override
    public final String toString() {
        return "ItemAddon[prefix: %s, author: %s, version: %s]".formatted(getPrefix(), getAuthor(), getVersion());
    }

}
