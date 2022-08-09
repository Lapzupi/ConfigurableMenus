package com.lapzupi.dev.configurablemenus.config.serializers;

import com.github.sarhatabaot.kraken.core.logging.LoggerUtil;
import com.lapzupi.dev.configurablemenus.ConfigurableMenusPlugin;
import com.lapzupi.dev.configurablemenus.menu.EmptyMenu;
import com.lapzupi.dev.configurablemenus.menu.model.Menu;
import com.lapzupi.dev.configurablemenus.menu.model.MenuItem;
import com.lapzupi.dev.configurablemenus.menu.model.MenuType;
import com.lapzupi.dev.configurablemenus.menu.types.GuiMenu;
import dev.triumphteam.gui.components.exception.GuiException;
import dev.triumphteam.gui.guis.Gui;
import org.bukkit.Bukkit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author sarhatabaot
 */
public class GuiMenuSerializer extends AbstractMenuSerializer<Gui> {
    private final Logger logger = LoggerFactory.getLogger(GuiMenuSerializer.class);
    private final ConfigurableMenusPlugin plugin = (ConfigurableMenusPlugin) Bukkit.getPluginManager().getPlugin("ConfigurableMenus");

    @Override
    public Menu<Gui> getMenu(final String id, final String permission, final String title, final MenuType menuType, final int rows, final List<MenuItem> items, final List<MenuItem> fillers) {
        try {
            return new GuiMenu(id, permission, title, menuType, rows, items, fillers);
        } catch (GuiException e){
            logger.info("There is a problem with this gui: %s. Returning empty menu.".formatted(id));
            plugin.debug(e.getMessage(), e);
            return new EmptyMenu(id, permission, title);
        }
    }
}
