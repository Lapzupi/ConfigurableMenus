package com.lapzupi.dev.configurablemenus.menu;

import com.lapzupi.dev.configurablemenus.menu.model.MenuType;
import com.lapzupi.dev.configurablemenus.menu.types.GuiMenu;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.exception.GuiException;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.Collections;

/**
 * @author sarhatabaot
 */
public class EmptyMenu extends GuiMenu {
    public EmptyMenu(final String id, final String permission, final String title) throws GuiException {
        super(id, permission, title, MenuType.CHEST, 3, Collections.emptyList(), Collections.emptyList());
        GuiItem guiItem = ItemBuilder.from(Material.BARRIER)
                .name(Component.text("Error"))
                .lore(Component.text("There was a problem with the configuration file for this menu")
                        .append(Component.text("Check your console for more info"))).asGuiItem();
        gui.disableAllInteractions();
        gui.setItem(2, 5, guiItem);
    }
}
