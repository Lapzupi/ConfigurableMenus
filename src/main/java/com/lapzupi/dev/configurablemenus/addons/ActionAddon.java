package com.lapzupi.dev.configurablemenus.addons;

import org.bukkit.entity.Player;

/**
 * @author sarhatabaot
 */
public abstract class ActionAddon implements Addon {

    //prefix:args
    //command:give {player} stick
    public abstract void onClick(final Player player, final String args);

    @Override
    public final String toString() {
        return "ActionAddon[prefix: %s, author: %s, version: %s]".formatted(getPrefix(), getAuthor(), getVersion());
    }
}
