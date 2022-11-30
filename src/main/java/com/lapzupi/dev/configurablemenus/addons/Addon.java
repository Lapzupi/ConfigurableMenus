package com.lapzupi.dev.configurablemenus.addons;

import org.bukkit.Bukkit;

/**
 * @author sarhatabaot
 */
public interface Addon {

    String EMPTY_URL = "";


    /**
     * @return The prefix used by the addon: "playerhead:id" or "base64:id in `prefix:id`"
     */
    String getPrefix();

    /**
     * @return The dependant plugin name, if there isn't one, you can set this to null.
     * You should override {@link #canRegister() canRegister() method} if there is no dependant plugin.
     */
    String getPluginName();


    /**
     * Can this addon be registered.
     */
    default boolean canRegister() {
        return (Bukkit.getPluginManager().getPlugin(getPluginName()) != null);
    }

    /**
     * @return The author's name
     */
    String getAuthor();

    /**
     * @return The addon's version
     */
    String getVersion();

    /**
     * @return Return a link to the addons project page or download link
     */
    default String getUrl() {
        return EMPTY_URL;
    }
}
