package com.lapzupi.dev.configurablemenus.hooks;

import com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin;
import me.arcaniax.hdb.api.DatabaseLoadEvent;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author sarhatabaot
 */
public class HdbWrapper implements Listener {
    private ConfigurableMenusPlugin plugin;
    private HeadDatabaseAPI api;

    public HdbWrapper(final ConfigurableMenusPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDatabaseLoad(DatabaseLoadEvent e) {
        this.api = new HeadDatabaseAPI();
    }

    public HeadDatabaseAPI getApi() {
        return api;
    }
}
