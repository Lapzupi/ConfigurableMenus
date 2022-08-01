package com.lapzupi.dev.configurablemenus.config.serializers;

import com.lapzupi.dev.configurablemenus.menu.model.Menu;
import com.lapzupi.dev.configurablemenus.menu.model.MenuItem;
import com.lapzupi.dev.configurablemenus.menu.model.MenuType;
import com.lapzupi.dev.configurablemenus.menu.types.GuiMenu;
import dev.triumphteam.gui.guis.Gui;

import java.util.List;

/**
 * @author sarhatabaot
 */
public class GuiMenuSerializer extends AbstractMenuSerializer<Gui> {
    @Override
    public Menu<Gui> getMenu(final String id, final String permission, final String title, final MenuType menuType, final int rows, final List<MenuItem> items, final List<MenuItem> fillers) {
        return new GuiMenu(id,permission,title, menuType,rows,items,fillers);
    }
}
