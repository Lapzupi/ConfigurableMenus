package com.lapzupi.dev.configurablemenus.menu.types;

import com.lapzupi.dev.configurablemenus.menu.model.Menu;
import com.lapzupi.dev.configurablemenus.menu.model.MenuItem;
import com.lapzupi.dev.configurablemenus.menu.model.MenuType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;

import java.util.List;

/**
 * @author sarhatabaot
 */
//todo not fully implemented
//Add static items option, ones that preserve across paginated pages..
public class PaginatedMenu extends Menu<PaginatedGui> {
    public PaginatedMenu(final String id, final String permission, final String title, final int rows, final List<MenuItem> items, final List<MenuItem> fillers) {
        super(id, permission, title, MenuType.PAGINATED, rows, items, fillers);
    }

    @Override
    public PaginatedGui createGuiFromType() {
        return Gui.paginated()
                .rows(rows)
                .title(Component.text(title))
                .disableAllInteractions()
                .create();
    }

    @Override
    public Menu<PaginatedGui> getMenu() {
        return this;
    }
}
