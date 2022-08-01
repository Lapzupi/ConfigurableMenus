package com.lapzupi.dev.configurablemenus.menu.types;

import com.lapzupi.dev.configurablemenus.menu.model.Menu;
import com.lapzupi.dev.configurablemenus.menu.model.MenuItem;
import com.lapzupi.dev.configurablemenus.menu.model.MenuType;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;

import java.util.List;

/**
 * @author sarhatabaot
 */
public class GuiMenu extends Menu<Gui> {
    public GuiMenu(final String id, final String permission, final String title, final MenuType type, final int rows, final List<MenuItem> items, final List<MenuItem> fillers) {
        super(id, permission, title, type, rows, items, fillers);
    }

    @Override
    public Gui createGuiFromType() {
        return Gui.gui(type.getGuiType())
                .rows(rows)
                .title(Component.text(title))
                .create();
    }

    @Override
    public Menu<Gui> getMenu() {
        return this;
    }
}
